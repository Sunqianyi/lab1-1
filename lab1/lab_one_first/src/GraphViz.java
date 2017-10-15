import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class GraphViz {
    private static String TEMP_DIR = "c:/temp";
    private static String DOT = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe";
    private StringBuilder graph = new StringBuilder();

    public GraphViz() {
    }

    public String getDotSource() {
        return this.graph.toString();
    }

    public void add(String line) {
        this.graph.append(line);
    }

    public void addln(String line) {
        this.graph.append(line + "\n");
    }

    public void addln() {
        this.graph.append('\n');
    }

    public byte[] getGraph(String dot_source, String type) {
        Object var4 = null;

        try {
            File dot = this.writeDotSourceToFile(dot_source);
            if (dot != null) {
                byte[] img_stream = this.get_img_stream(dot, type);
                if (!dot.delete()) {
                    System.err.println("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
                }

                return img_stream;
            } else {
                return null;
            }
        } catch (IOException var6) {
            return null;
        }
    }

    public int writeGraphToFile(byte[] img, String file) {
        File to = new File(file);
        return this.writeGraphToFile(img, to);
    }

    public int writeGraphToFile(byte[] img, File to) {
        try {
            FileOutputStream fos = new FileOutputStream(to);
            fos.write(img);
            fos.close();
            return 1;
        } catch (IOException var4) {
            var4.printStackTrace();
            return -1;
        }
    }

    private byte[] get_img_stream(File dot, String type) {
        byte[] img_stream = null;

        try {
            File img = File.createTempFile("graph_", "." + type, new File(TEMP_DIR));
            Runtime rt = Runtime.getRuntime();
            String[] args = new String[]{DOT, "-T" + type, dot.getAbsolutePath(), "-o", img.getAbsolutePath()};
            Process p = rt.exec(args);
            p.waitFor();
            FileInputStream in = new FileInputStream(img.getAbsolutePath());
            img_stream = new byte[in.available()];
            in.read(img_stream);
            if (in != null) {
                in.close();
            }

            if (!img.delete()) {
                System.err.println("Warning: " + img.getAbsolutePath() + " could not be deleted!");
            }
        } catch (IOException var9) {
            System.err.println("Error:    in I/O processing of tempfile in dir " + TEMP_DIR + "\n");
            System.err.println("       or in calling external command");
            var9.printStackTrace();
        } catch (InterruptedException var10) {
            System.err.println("Error: the execution of the external program was interrupted");
            var10.printStackTrace();
        }

        return img_stream;
    }

    public File writeDotSourceToFile(String str) throws IOException {
        try {
            File temp = File.createTempFile("graph_", ".dot.tmp", new File(TEMP_DIR));
            FileWriter fout = new FileWriter(temp);
            fout.write(str);
            fout.close();
            return temp;
        } catch (Exception var4) {
            System.err.println("Error: I/O error while writing the dot source to temp file!");
            return null;
        }
    }

    public String start_graph() {
        return "digraph G {";
    }

    public String end_graph() {
        return "}";
    }

    public void readSource(String input) {
        StringBuilder sb = new StringBuilder();

        try {
            FileInputStream fis = new FileInputStream(input);
            DataInputStream dis = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));

            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
            }

            dis.close();
        } catch (Exception var7) {
            System.err.println("Error: " + var7.getMessage());
        }

        this.graph = sb;
    }

//sssss
}