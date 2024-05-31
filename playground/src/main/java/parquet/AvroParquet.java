package parquet;

import org.apache.avro.Schema;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroSchemaConverter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.schema.MessageType;
import parquet.org.jam.UserRank;

// reference - http://www.hydrogen18.com/blog/writing-parquet-records.html
// ref 2 - https://github.com/MaxNevermind/Hadoop-snippets/blob/master/src/main/java/org/maxkons/hadoop_snippets/parquet/ParquetReaderWriterWithAvro.java

public class AvroParquet {
    public static void main(String[] args) {

        Schema avroSchema = UserRank.getClassSchema();
        MessageType parquetSchema = new AvroSchemaConverter().convert(avroSchema);

        UserRank[] data = new UserRank[] {
                new UserRank(1, 10),
                new UserRank(2, 8),
                new UserRank(3, 4),
                new UserRank(4, 1)
        };

        Path filePath = new Path("./userRank.parquet");
        int pageSize = 65535;
        try(
                ParquetWriter<UserRank> writer = AvroParquetWriter
                        .<UserRank>builder(filePath)
                        .withSchema(avroSchema)
                        .withConf(new Configuration())
                        .withCompressionCodec(CompressionCodecName.SNAPPY)
                        .withPageSize(pageSize)
                        .build()
        ){
            for(UserRank obj : data){
                writer.write(obj);
            }
        }catch(java.io.IOException e){
            System.out.println(String.format("Error writing parquet file %s", e.getMessage()));
            e.printStackTrace();
        }
    }
}
