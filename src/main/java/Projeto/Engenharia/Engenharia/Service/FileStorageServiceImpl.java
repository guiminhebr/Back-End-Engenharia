package Projeto.Engenharia.Engenharia.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class FileStorageServiceImpl implements FilesStorageService {

    @Value("${aws.bucketName}")
    private String bucketName;

    private final S3Client s3Client;

    public FileStorageServiceImpl(
            @Value("${aws.accessKeyId}") String accessKey,
            @Value("${aws.secretKey}") String secretKey,
            @Value("${aws.region}") String region) {

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Override
    public void init() {
        // Não precisa fazer nada — o S3 não precisa de pasta local
    }

    @Override
    public void save(MultipartFile file) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(file.getOriginalFilename())
                            .contentType(file.getContentType())
                            .build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar no S3: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String filename) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build());
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar do S3: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            ResponseBytes<GetObjectResponse> obj = s3Client.getObjectAsBytes(
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(filename)
                            .build()
            );
            return new ByteArrayResource(obj.asByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar do S3: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        // Opcional — limpa tudo do bucket se precisar
    }

    @Override
    public Stream<Path> loadAll() {
        ListObjectsV2Response response = s3Client.listObjectsV2(
                ListObjectsV2Request.builder()
                        .bucket(bucketName)
                        .build()
        );
        return response.contents().stream()
                .map(obj -> Paths.get(obj.key()));
    }
}