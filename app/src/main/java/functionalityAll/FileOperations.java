package functionalityAll;


import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import controllerAll.Config;

public class FileOperations {

    public boolean isFileExist(String imageName, String subFolderName) {
        File myDir = new File(Environment.getExternalStorageDirectory(), Config.FOLDER_NAME);

        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File mySubDir = new File(myDir, subFolderName);

        if (!mySubDir.exists()) {
            mySubDir.mkdir();
        }


        File file = new File(mySubDir, imageName);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFileExistInGallery(String imageName, String subFolderName) {
        File myDir = new File(Environment.getExternalStorageDirectory(), Config.FOLDER_NAME_GALLERY);

        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File my_sub_Dir = new File(myDir, subFolderName);

        if (!my_sub_Dir.exists()) {
            my_sub_Dir.mkdir();
        }


        File file = new File(my_sub_Dir, imageName);

        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }


    public String getFileName(String imageName, String subFolderName) {

        File myDir = new File(Environment.getExternalStorageDirectory(), Config.FOLDER_NAME);

        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File mySubDir = new File(myDir, subFolderName);

        if (!mySubDir.exists()) {
            mySubDir.mkdir();
        }


        File file = new File(mySubDir, imageName);
        String CurrentString = file.toString();

        String separated = CurrentString.substring(CurrentString.lastIndexOf("/") + 1);

        return separated.trim();
    }

    public String getFileNameFromGallery(String imageName, String subFolderName) {

        File myDir = new File(Environment.getExternalStorageDirectory(), Config.FOLDER_NAME_GALLERY);

        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File my_sub_Dir = new File(myDir, subFolderName);

        if (!my_sub_Dir.exists()) {
            my_sub_Dir.mkdir();
        }


        File file = new File(my_sub_Dir, imageName);
        String CurrentString = file.toString();

        String separated = CurrentString.substring(CurrentString.lastIndexOf("/") + 1);

        return separated.trim();
    }

    public String getFilePath(String imageName, String subFolderName) {
        File myDir = new File(Environment.getExternalStorageDirectory(), Config.FOLDER_NAME);
        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File my_sub_Dir = new File(myDir, subFolderName);
        if (!my_sub_Dir.exists()) {
            my_sub_Dir.mkdir();
        }


        File file = new File(my_sub_Dir, imageName);
        String CurrentString = file.toString();

        return CurrentString.trim();
    }

    public String getFilePathOfGallery(String imageName, String subFolderName) {

        File myDir = new File(Environment.getExternalStorageDirectory(), Config.FOLDER_NAME_GALLERY);

        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File my_sub_Dir = new File(myDir, subFolderName);

        if (!my_sub_Dir.exists()) {
            my_sub_Dir.mkdir();
        }


        File file = new File(my_sub_Dir, imageName);
        String CurrentString = file.toString();

        return CurrentString.trim();
    }

    public String getDestinationPath(String subFolderName){
        File myDir = new File(Environment.getExternalStorageDirectory(), Config.FOLDER_NAME);

        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File my_sub_Dir = new File(myDir, subFolderName);
        if (!my_sub_Dir.exists()) {
            my_sub_Dir.mkdir();
        }

        String CurrentString = my_sub_Dir.toString();

        return CurrentString;
    }

    public String getDestinationPathVisibileToGallery(String sub_folder_name){
        File myDir = new File(Environment.getExternalStorageDirectory(), Config.FOLDER_NAME_GALLERY);

        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File my_sub_Dir = new File(myDir, sub_folder_name);
        if (!my_sub_Dir.exists()) {
            my_sub_Dir.mkdir();
        }

        String CurrentString = my_sub_Dir.toString();

        return CurrentString;
    }

    public void copyFile(File sourceFile, File destFile) {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();

            if (destination != null && source != null) {
                destination.transferFrom(source, 0, source.size());
            }
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }

        } catch (Exception e) {
            CatchResponse.Report(e);
            e.printStackTrace();
        }
    }
}
