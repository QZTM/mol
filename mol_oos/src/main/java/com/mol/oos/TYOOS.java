package com.mol.oos;

import java.io.*;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class TYOOS {

    private static AmazonS3 oos = OOSClient.getClient();

    private TYOOS(){
    }

    /**
     * 列出所有的bucket
     */
    public static void bucketList(){
        /*列出账户内的所有 buckets */
        System.out.println("Listing buckets");
        for (Bucket bucket : oos.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
    }

    /**
     * 创建bucket
     * @param bucketName
     */
    public static void createBucket(String bucketName){
        /* 创建 bucket */
        System.out.println("Creating bucket " + bucketName + "\n");
        oos.createBucket(bucketName);
        System.out.println();
    }

    /**
     * 上传一个文件
     * @param bucketName        桶容器名称
     * @param key               文件名
     * @param file              文件
     */
    public static void uploadObjToBucket(String bucketName,String key,File file) throws IOException {
        /* 上传一个 object 到 bucket 中 */
        System.out.println("Uploading a new object to OOS from a file\n");
            oos.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));
    }

    /**
     * 下载
     * @param bucketName
     * @param key
     * @throws IOException
     */
    public static void download(String bucketName,String key) throws IOException {
        /* 下载 object */
        System.out.println("Downloading an object");
            /* 当使用getObject()方法时，需要非常小心。因为返回的S3Object对象包
            括一个从HTTP连接获得的数据流。底层的HTTP连接不会被关闭，直到用户
            读完了数据，并关闭了流。因此从S3Object中读取inputStream数据后，需要立刻关闭流。
            否则会导致客户端连接池用满 */
        S3Object object = oos.getObject(new GetObjectRequest(bucketName, key));
        System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
        System.out.println("Content:");
        displayTextInputStream(object.getObjectContent());
    }


    /**
     *  拷贝 object
     * @param bucketName
     * @param key
     * @param destinationBucketName
     * @param destinationKey
     */
    public static void copy(String bucketName,String key,String destinationBucketName,String destinationKey){
        /* 拷贝 object */
//        String destinationBucketName = "my-copy-oos-bucket";
//        String destinationKey = "MyCopyKey";
        System.out.println("Copying an object ,from " + bucketName + "/" + key + " to " + destinationBucketName + "/" + destinationKey);
        oos.createBucket(destinationBucketName);
        oos.copyObject(bucketName, key, destinationBucketName, destinationKey);
    }


    /**
     * 下载拷贝的 object
     * @param bucketName
     * @param key
     * @param destinationKey
     * @param destinationBucketName
     * @throws IOException
     */
    public static void downCoped(String bucketName,String key,String destinationKey,String destinationBucketName) throws IOException {
        /* 下载拷贝的 object */
        S3Object object = oos.getObject(new GetObjectRequest(bucketName, key));
        System.out.println("Downloading the " + destinationKey + " object");
        object = oos.getObject(new GetObjectRequest(destinationBucketName, destinationKey));
        System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
        System.out.println("Content:");
        displayTextInputStream(object.getObjectContent());
    }


    /**
     * 列出 bucket 中的 object
     * @param bucketName        桶名称
     * @param filePre           文件前缀
     */
    public static void listObj(String bucketName,String filePre){
        /* 列出 bucket 中的 object，支 prefix,delimiter,marker,max-keys 等选项 */
        if(filePre == null){
            filePre = "";
        }
        System.out.println("Listing objects");
        ObjectListing objectListing = oos.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix(filePre));
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
        }
        System.out.println();
    }


    /**
     * 删除 object
     * @param bucketName
     * @param key
     * @param destinationBucketName
     * @param destinationKey
     */
    public static void delObj(String bucketName,String key,String destinationBucketName,String destinationKey){
        /* 删除 object */
        System.out.println("Deleting objects\n");
        oos.deleteObject(bucketName, key);
        oos.deleteObject(destinationBucketName, destinationKey);
    }


    /**
     * 删除bucket
     * @param bucketName
     * @param destinationBucketName
     */
    public static void delBucket(String bucketName,String destinationBucketName){
        /* 删除 bucket */
        System.out.println("Deleting bucket " + bucketName + "\n");
        oos.deleteBucket(bucketName);
        System.out.println("Deleting bucket " + destinationBucketName + "\n");
        oos.deleteBucket(destinationBucketName);
    }


    public static File createSampleFile() throws IOException {
        File file = File.createTempFile("oos-java-sdk-", ".txt");
        file.deleteOnExit();
        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghuvwxyz\n");
        writer.write("0122345678901234\n");
        writer.write("!@#$':',.<>/?\n");
        writer.write("012345678901234\n");
        writer.write("abcderstuvwxyz\n");
        writer.close();
        return file;
    }


    public static void getBucketLocation(String bucketName){
        String locationResult = oos.getBucketLocation(bucketName);
        System.out.println("bucketLocation:"+locationResult);
    }


    /**
     * 关闭资源占用
     * @param input
     * @throws IOException
     */
    public static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            System.out.println("    " + line);
        } /* 需要在这里关闭InputStream，原因参见getObject处 */
        input.close();
        System.out.println();
    }

}
