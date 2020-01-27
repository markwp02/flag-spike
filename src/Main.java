import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("-----Move Inbox to Outbox------");
        Path dirInboxPath;
        Path dirOutboxPath;
        Path inboxPath = FileSystems.getDefault().getPath("Inbox");
        Path outboxPath = FileSystems.getDefault().getPath("Outbox");
        DirectoryStream.Filter<Path> dirFilter = p -> Files.isDirectory(p);

        try (DirectoryStream<Path> contents = Files.newDirectoryStream(inboxPath, dirFilter)){
            for(Path dir: contents) {
                System.out.println(dir.toAbsolutePath());
                Path relatavisedPath = inboxPath.relativize(dir);
                dirInboxPath = inboxPath.resolve(relatavisedPath);
                dirOutboxPath = outboxPath.resolve(relatavisedPath);
                Files.walkFileTree(dirInboxPath, new MoveFiles(dirInboxPath, dirOutboxPath));
            }
        } catch (IOException | DirectoryIteratorException i ) {
            System.out.println(i.getMessage());
        }

    }
}
