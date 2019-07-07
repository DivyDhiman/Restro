package functionalityAll;

import android.content.Context;


import java.net.URISyntaxException;

import callBacks.SocketCallBack;
import controllerAll.Config;

/**
 * Created by Abhay dhiman
 */

public class CentraliseSocketFunction {
//    private Socket mSocket;
//    private SocketCallBack socketCallBack;
//    private String userID, groupID;

    public void socketInit(final Context context, Object... arg) {
//        socketCallBack = (SocketCallBack) arg[0];
//        userID = (String) arg[1];
//        groupID = (String) arg[2];
//
//        try {
//            mSocket = IO.socket(Config.SOCKET_URL);
//            mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//
//                @Override
//                public void call(final Object... args) {
//                    socketCallBack.socketData(context.getString(R.string.connect_socket), args);
//                }
//
//            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//                @Override
//                public void call(Object... arg0) {
//                    socketCallBack.socketData(context.getString(R.string.disconnect_socket), arg0);
//                }
//            }).on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
//                @Override
//                public void call(Object... arg0) {
//                    socketCallBack.socketData(context.getString(R.string.connection_error), arg0);
//                }
//            }).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
//
//                @Override
//                public void call(Object... arg0) {
//                    socketCallBack.socketData(context.getString(R.string.connection_timeout), arg0);
//                }
//            }).on(Socket.EVENT_CONNECTING, new Emitter.Listener() {
//
//                @Override
//                public void call(Object... arg0) {
//                    socketCallBack.socketData(context.getString(R.string.connecting_socket), arg0);
//                }
//            }).on(Socket.EVENT_ERROR, new Emitter.Listener() {
//
//                @Override
//                public void call(Object... arg0) {
//                    socketCallBack.socketData(context.getString(R.string.event_error), arg0);
//                }
//            }).on("getMessage", new Emitter.Listener() {
//                @Override
//                public void call(Object... args) {
//                    socketCallBack.socketData(context.getString(R.string.get_message_socket), args);
//                }
//            }).on("typing", new Emitter.Listener() {
//                @Override
//                public void call(final Object... args) {
//                    socketCallBack.socketData(context.getString(R.string.typing_socket), args);
//                }
//            }).on("messageDelete", new Emitter.Listener() {
//                @Override
//                public void call(final Object... args) {
//                    socketCallBack.socketData(context.getString(R.string.message_delete), args);
//                }
//            }).on("messageRead", new Emitter.Listener() {
//                @Override
//                public void call(final Object... args) {
//                    socketCallBack.socketData(context.getString(R.string.message_read), args);
//                }
//            });
//            mSocket.connect();
//        } catch (URISyntaxException e) {
//            socketCallBack.socketData(context.getString(R.string.socket_error), e);
//            CatchList.Report(e);
//        }
    }

    public void socketEmit(Object... data) {
//        if (mSocket != null) {
//            mSocket.emit(String.valueOf(data[0]), data[1]);
//        }
    }


    public void disconnectSocket(String key) {
//        if (mSocket != null) {
//            mSocket.emit(key);
////            mSocket.disconnect();
//        }
    }
}
