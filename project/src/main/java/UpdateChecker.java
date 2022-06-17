import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class UpdateChecker {

    private String versionFileId = "1cmIDc3y98YPKjAqUj8NSKrQVOpUMYGmL";
    private String exeFileId = "1NsHoKzCe75Yl7ReI_UJatLD270tbXTUX";
    private String pckFileId = "1Xb1xKNpxWyufy82NSgfwqDM_e55J0AeT";
    private String APIkey = "AIzaSyD64yK-dwZXNak53Rs99_nequqrvyfpyjs";

    public UpdateChecker() {
    }

    public void check() {
        try {
            File versionFile = new File("version.txt");
            File gameDir = new File(System.getProperty("user.dir") + "\\game");
            if (!gameDir.exists()) {
                gameDir.mkdirs();
            }
            if (versionFile.createNewFile()) {
                /* Game is not downloaded yet (or someone deleted the version file) */
                boolean canRead = readDriveFile(versionFileId, "version.txt");
                if (!canRead) {
                    return;
                }
                update();
                startGame();
            } else {
                /* Game is downloaded, we need to check version */
                boolean canRead = readDriveFile(versionFileId, "new_version.txt");
                if (!canRead) {
                    return;
                }
                File newVersionFile = new File("new_version.txt");
                Scanner oldVersionScanner = new Scanner(versionFile);
                Scanner newVersionScanner = new Scanner(newVersionFile);

                String oldVersion = oldVersionScanner.nextLine();
                int[] oldVersionNumbers = Arrays.stream(oldVersion.split("\\.")).mapToInt(Integer::parseInt).toArray();
                String newVersion = newVersionScanner.nextLine();
                int[] newVersionNumbers = Arrays.stream(newVersion.split("\\.")).mapToInt(Integer::parseInt).toArray();

                int iVersionNumbers = 0;
                int minVersionLength = Math.min(oldVersionNumbers.length, newVersionNumbers.length);

                /* Compare the versions such that *.*.* is allowed as a version format */
                while (iVersionNumbers < minVersionLength) {
                    if (newVersionNumbers[iVersionNumbers] > oldVersionNumbers[iVersionNumbers]) {
                        promptUpdate();
                        FileWriter writer = new FileWriter(versionFile);
                        writer.write(newVersion);
                        writer.close();
                        break;
                    }
                    iVersionNumbers++;
                }
                /* new version has an added version number added after the dot (1.2.1 is newer than 1.2) */
                if (iVersionNumbers == minVersionLength && oldVersionNumbers.length < newVersionNumbers.length) {
                    promptUpdate();
                }
                oldVersionScanner.close();
                newVersionScanner.close();
                newVersionFile.delete();
                startGame();
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getDriveURL(String id) {
        try {
            return new URL("https://www.googleapis.com/drive/v3/files/" + id + "?key=" + APIkey + "&alt=media");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean readDriveFile(String fileId, String filepath) {
        ReadableByteChannel rbc = null;
        boolean success = false;
        try {
            rbc = Channels.newChannel(getDriveURL(fileId).openStream());
            File out = new File(filepath);
            FileOutputStream fos = new FileOutputStream(out);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            success = true;
        } catch (IOException e) {
            ErrorPromptPanel.prompt();
        }
        return success;
    }

    private void promptUpdate() {
        System.out.println("There is a new update!");
        boolean wantUpdate = UpdatePromptPanel.prompt();
        if (wantUpdate) {
            update();
        }
    }

    private void update() {
        UpdatingWindow panel = new UpdatingWindow();
        panel.prompt();

        readDriveFile(exeFileId, "game/dasblockchen.exe");
        readDriveFile(pckFileId, "game/dasblockchen.pck");
        panel.close();
    }

    private void startGame() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(System.getProperty("user.dir") + "\\game\\dasblockchen.exe");
        try {
            processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
