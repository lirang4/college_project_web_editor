package analyzer.Compile;

import org.apache.commons.lang.mutable.MutableBoolean;

import java.io.*;

public class ProgramCompiler {

    public void createCFile(String code, File file)
    {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(code);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String compile(File file, MutableBoolean isFailed) {
        String log = "";
        try {
            String s = null;
            Process p = Runtime.getRuntime().exec("cmd /C  \"C:\\Program Files (x86)\\CodeBlocks\\MinGW\\bin\\mingw32-g++.exe\" " + file.getAbsolutePath());

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            log += "\n....\n";
            while ((s = stdError.readLine()) != null) {
                log += s;
                isFailed.setValue(true);
                log += "\n";
            }
            if (isFailed.getValue().equals(false)) log += "Compilation successful !!!";

        } catch (IOException e) {
            e.printStackTrace();
        }
        return log;
    }
}

