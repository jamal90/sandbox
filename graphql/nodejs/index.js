import { ApolloServer } from "@apollo/server"
import { startStandaloneServer } from "@apollo/server/standalone"
import { typeDefs } from "./schema.js"
import data from "./data.js"

let gameSeq = 10
const resolvers = {
    Query: {
        games() {
            return data.games
        },
        authors() {
            return data.authors
        },
        reviews() {
            return data.reviews
        },
        review(_, args, ctx) {
            return data.reviews.find(review =>  review.id === args.id)
        },
        game(_, args, ctx) {
            return data.games.find(game =>  game.id === args.id)
        },
        author(_, args, ctx) {
            return data.authors.find(author =>  author.id === args.id)
        }
    },
    Game: {
        reviews(parent) {
            return data.reviews.filter(review => review.game_id === parent.id)
        }
    },
    Author: {
        reviews(parent) {
            return data.reviews.filter(review => review.author_id === parent.id)
        }
    },
    Review: {
        game(parent) {
            return data.games.find(game => game.id === parent.game_id)
        },
        author(parent) {
            return data.authors.find(author => author.id === parent.author_id)
        }
    },
    Mutation: {
        deleteGame(_, args) {
            data.games = data.games.filter(game => game.id !== args.id)
            return data.games
        },
        addGame(_, args) {
            const newGame = {...args.game, id: gameSeq++}
            data.games.push(newGame)
            return newGame
        },
        updateGame(_, args) {
            data.games = data.games.map(game => {
                console.log(game.id)
                if (game.id === args.id) {
                    return {
                        ...game, 
                        ...args.game
                    }
                }

                return game
            })
            return data.games.find(game => game.id === args.id)
        }
    }
}

// server setup
const server = new ApolloServer({
    typeDefs,
    resolvers
})

const { url } = await startStandaloneServer(server, {
    listen: { port: 4000 }
})

console.log(`server running on port ${url}`)
