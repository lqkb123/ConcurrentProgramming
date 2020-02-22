package com.liqiang.concurrent.chpater4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.StringTokenizer;

/**
 * 一个接收浏览器HTTP请求并返回数据的demo
 * 主要验证PrintWriter为何无法成功返回数据(原因是PrintWriter输出完必须立即flush())
 * PrintStream会自动flush,源码如下:
 *   textOut.flushBuffer();
 *   charOut.flushBuffer();
 */
public class SocketHttpServer implements Runnable {

    private final static int PORT = 8080;
    private ServerSocket server = null;

    public static void main(String[] args) {
        new SocketHttpServer();
    }

    public SocketHttpServer() {
        try {
            server = new ServerSocket(PORT);
            if (server == null)
                System.exit(1);
            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket client = null;
                client = server.accept();
                if (client != null) {
                    try {
                        System.out.println("连接服务器成功！！...");

                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(client.getInputStream()));

                        // GET /test.jpg /HTTP1.1
                        String line = reader.readLine();

                        System.out.println("line: " + line);

                        String resource = line.substring(line.indexOf('/'),
                                line.lastIndexOf('/') - 5);

                        System.out.println("the resource you request is: "
                                + resource);

                        resource = URLDecoder.decode(resource, "UTF-8");

                        String method = new StringTokenizer(line).nextElement()
                                .toString();

                        System.out.println("the request method you send is: "
                                + method);

                        while ((line = reader.readLine()) != null) {
                            if (line.equals("")) {
                                break;
                            }
                            System.out.println("the Http Header is : " + line);
                        }

                        if ("post".equals(method.toLowerCase())) {
                            System.out.println("the post request body is: "
                                    + reader.readLine());
                        }

                        if (resource.endsWith(".mkv")) {

                            transferFileHandle("videos/test.mkv", client);
                            closeSocket(client);
                            continue;

                        } else if (resource.endsWith(".jpg")) {

                            transferFileHandle("D:/img/1.jpg", client);
                            closeSocket(client);
                            continue;

                        } else if (resource.endsWith(".rmvb")) {

                            transferFileHandle("videos/test.rmvb", client);
                            closeSocket(client);
                            continue;

                        } else {
                            PrintStream writer = new PrintStream(
                                    client.getOutputStream(), true);
                            writer.println("HTTP/1.0 404 Not found");// 返回应答消息,并结束应答
                            writer.println();// 根据 HTTP 协议, 空行将结束头信息
                            writer.close();
                            closeSocket(client);
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("HTTP服务器错误:"
                                + e.getLocalizedMessage());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void closeSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(socket + "离开了HTTP服务器");
    }

    private void transferFileHandle(String path, Socket client) {

        File fileToSend = new File(path);

        if (fileToSend.exists() && !fileToSend.isDirectory()) {
            try {
                PrintStream writer = new PrintStream(client.getOutputStream());
//                PrintWriter writer = new PrintWriter(client.getOutputStream());
                InputStream in = new FileInputStream(path);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int i = 0;
                while ((i = in.read()) != -1) {
                    baos.write(i);
                }
                byte[] array = baos.toByteArray();
//                writer.println("HTTP/1.0 200 OK");// 返回应答消息,并结束应答
//                writer.println("Content-Type:application/binary");
//                writer.println("Content-Length:" + fileToSend.length());// 返回内容字节数
//                writer.println();// 根据 HTTP 协议, 空行将结束头信息
                writer.println("HTTP/1.1 200 ok");
                writer.println("HOST: localhost:8080");
                writer.println("Server: Molly");
                writer.println("Content-Type: image/jpeg");
                writer.println("Content-Length: " + fileToSend.length());
                writer.println("");
//                writer.flush(); //PrintStream不用flush(),但是PrintWriter输出完请求头必须刷新
                client.getOutputStream().write(array, 0, array.length);
//                FileInputStream fis = new FileInputStream(fileToSend);
//                byte[] buf = new byte[fis.available()];
//                fis.read(buf);
//                writer.write(buf);
//                writer.close();
//                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
