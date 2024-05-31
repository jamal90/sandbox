package streams;

import java.io.*;

public class FileSizeCheck {

    private static final long MAX_FILE_SIZE = 30 * 1024 * 1024; // in bytes

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("/Users/i076097/Desktop/tmp/aggr-logs.txt");
        SizeRestrictingInputStream sizeRestrictedStream = new SizeRestrictingInputStream(fis, 5 * 1024 * 1024);

        new BufferedReader(new InputStreamReader(sizeRestrictedStream))
                .lines()
                .forEach(System.out::println);
    }

    static class SizeRestrictingInputStream extends FilterInputStream {

        private final long maxSize;
        private long currSize = 0L;

        /**
         * Creates a <code>FilterInputStream</code>
         * by assigning the  argument <code>in</code>
         * to the field <code>this.in</code> so as
         * to remember it for later use.
         *
         * @param in the underlying input stream, or <code>null</code> if
         *           this instance is to be created without an underlying stream.
         */
        protected SizeRestrictingInputStream(InputStream in) {
            super(in);
            this.maxSize = MAX_FILE_SIZE; // default
        }

        public SizeRestrictingInputStream(InputStream in, long maxSize) {
            super(in);
            this.maxSize = maxSize;
        }

        @Override
        public int read() throws IOException {
            currSize++;
            restrictSize();
            return super.read();
        }

        private void restrictSize() throws IOException{
            if (currSize > maxSize)
                throw new IOException("File Size limit exceeded");
        }

        @Override
        public int read(byte[] b) throws IOException {
            currSize += b.length;
            restrictSize();
            return super.read(b);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            currSize += len;
            restrictSize();
            return super.read(b, off, len);
        }
    }
}
