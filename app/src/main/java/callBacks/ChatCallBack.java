package callBacks;


public interface ChatCallBack {
    void deleteData(String message_id, String user_id, String type_lr, int pos);

    void getDataImage(Object... args);

    void getDataVideo(Object... args);

    void isReadData();

    void getArrayData(Object... args);
}
