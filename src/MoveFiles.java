import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MoveFiles extends SimpleFileVisitor<Path> {

    private Path sourceRoot;
    private Path targetRoot;

    public MoveFiles(Path sourceRoot, Path targetRoot) {
         this.sourceRoot = sourceRoot;
        this.targetRoot = targetRoot;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {

        Path relativizedPath = sourceRoot.relativize(dir);
        if(!hasFlagFile(dir)){
            System.out.println("No flag file present. Skipping directory");
            return FileVisitResult.SKIP_SUBTREE;
        }
        System.out.println("RelativizedPath = " + relativizedPath);
        Path moveDir = targetRoot.resolve(relativizedPath);
        System.out.println("Resolved path for move = " + moveDir);

        try {
            Files.move(dir, moveDir);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return FileVisitResult.SKIP_SUBTREE;
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Error accessing file: " + file.toAbsolutePath() + " " + exc.getMessage());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path relativizedPath = sourceRoot.relativize(file);

        Path moveDir = targetRoot.resolve(relativizedPath);
        System.out.println("Resolved path for copy = " + moveDir);

        try {
            Files.move(file, moveDir);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return FileVisitResult.CONTINUE;
    }

    private boolean hasFlagFile(Path dir) {

        try (DirectoryStream<Path> contents = Files.newDirectoryStream(dir, "*.flag")) {
            for (Path file : contents) {
                System.out.println(file.getFileName());
                return true;
            }
            return false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
