import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        System.out.println("-----Move Inbox to Outbox------");
        Path dir1Path = FileSystems.getDefault().getPath("Inbox", "Dir1");
        Path dir2Path = FileSystems.getDefault().getPath("Inbox", "Dir2");

        Path outboxDir1 = FileSystems.getDefault().getPath("Outbox", "Dir1");
        Path outboxDir2 = FileSystems.getDefault().getPath("Outbox", "Dir2");

        try{
            Files.walkFileTree(dir1Path, new MoveFiles(dir1Path, outboxDir1));
            Files.walkFileTree(dir2Path, new MoveFiles(dir2Path, outboxDir2));
        } catch (IOException i) {
            System.out.println(i.getMessage());
        }

    }
}
