package parquet;

import org.apache.avro.Schema;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroSchemaConverter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;
import parquet.org.jam.AddressRecord;
import parquet.org.jam.User;

// reference - http://www.hydrogen18.com/blog/writing-parquet-records.html
// ref 2 - https://github.com/MaxNevermind/Hadoop-snippets/blob/master/src/main/java/org/maxkons/hadoop_snippets/parquet/ParquetReaderWriterWithAvro.java

public class AvroParquetNested {
    public static void main(String[] args) {

        Schema avroSchema = User.getClassSchema();
        MessageType parquetSchema = new AvroSchemaConverter().convert(avroSchema);

        User[] data = new User[] {
                new User(1, "abc", new AddressRecord(10, "san juan rd")),
                new User(2, "def", new AddressRecord(12, "san juan rd")),
                new User(3, "iam", new AddressRecord(13, "san juan rd")),
                new User(4, "jam", new AddressRecord(15, "san juan rd")),
        };

        Path filePath = new Path("./user.parquet");
        int pageSize = 65535;
        try(
                ParquetWriter<User> writer = AvroParquetWriter
                        .<User>builder(filePath)
                        .withSchema(avroSchema)
                        .withConf(new Configuration())
                        .withCompressionCodec(CompressionCodecName.SNAPPY)
                        .withPageSize(pageSize)
                        .build()
        ){
            for(User obj : data){
                writer.write(obj);
            }
        }catch(java.io.IOException e){
            System.out.println(String.format("Error writing parquet file %s", e.getMessage()));
            e.printStackTrace();
        }
    }
}
