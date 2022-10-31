package com.hyj.nio.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class SocketT1 {

    private static String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><service><sys-header><data name=\"SYS_HEAD\"><struct><data name=\"SERVICE_VER\"><field type=\"string\" length=\"30\"/></data><data name=\"SERVICE_CODE\"><field type=\"string\" length=\"30\">3053000005</field></data><data name=\"SERVICE_SCENE\"><field type=\"string\" length=\"30\">03</field></data><data name=\"CONSUMER_SYS_ID\"><field type=\"string\" length=\"5\">33200</field></data><data name=\"ORIG_SYS_ID\"><field type=\"string\" length=\"5\"/></data><data name=\"ORIG_SYS_TMNL_NO\"><field type=\"string\" length=\"50\"/></data><data name=\"ORIG_SYS_SERVER_ID\"><field type=\"string\" length=\"50\"/></data><data name=\"CONSUMER_SYS_SERVER_ID\"><field type=\"string\" length=\"50\">127.0.0.1</field></data><data name=\"CONSUMER_SYS_SEQ_NO\"><field type=\"string\" length=\"21\">DS00911141918614</field></data><data name=\"TRAN_MODE\"><field type=\"string\" length=\"10\">ONLINE</field></data><data name=\"SOURCE_TYPE\"><field type=\"string\" length=\"2\">DS</field></data><data name=\"TRAN_DATE\"><field type=\"string\" length=\"8\">20210528</field></data><data name=\"TRAN_TIMESTAMP\"><field type=\"string\" length=\"9\">141918614</field></data><data name=\"WS_ID\"><field type=\"string\" length=\"30\">05</field></data><data name=\"USER_LANG\"><field type=\"string\" length=\"20\">CHINESE</field></data><data name=\"SOURCE_BRANCH_NO\"><field type=\"string\" length=\"6\"/></data><data name=\"DEST_BRANCH_NO\"><field type=\"string\" length=\"6\">2400</field></data><data name=\"FILE_FLAG\"><field type=\"string\" length=\"1\"/></data><data name=\"FILE_PATH\"><field type=\"string\" length=\"512\"/></data><data name=\"GLOBAL_SEQ_NO\"><field type=\"string\" length=\"52\"/></data><data name=\"EXTEND_FIELD1\"><field type=\"string\" length=\"512\"/></data><data name=\"EXTEND_FIELD2\"><field type=\"string\" length=\"512\"/></data></struct></data></sys-header><app-header><data name=\"APP_HEAD\"><struct><data name=\"BRANCH_ID\"><field type=\"string\" length=\"30\">60001</field></data><data name=\"USER_ID\"><field type=\"string\" length=\"30\">00018</field></data><data name=\"AUTH_USER_ID\"><field type=\"string\" length=\"30\"/></data><data name=\"AUTH_PASSWORD\"><field type=\"string\" length=\"30\"/></data><data name=\"AUTH_FLAG\"><field type=\"string\" length=\"1\"/></data><data name=\"APPR_USER_ID\"><field type=\"string\" length=\"30\"/></data><data name=\"APPR_FLAG\"><field type=\"string\" length=\"12\"/></data></struct></data></app-header><local-head><data name=\"LOCAL_HEAD\"><struct><data name=\"RET_AUTH_ARRAY\"><array><struct><data name=\"RET_CODE\"><field length=\"6\" type=\"string\"/></data><data name=\"RET_MSG\"><field length=\"512\" type=\"string\"/></data></struct></array></data></struct></data></local-head><body><data name=\"PG_UP_PG_DN_FLAG\"><field length=\"1\" type=\"string\"/></data><data name=\"CUR_PAGE_RECORD_TOTAL_CNT\"><field length=\"10\" type=\"string\"/></data><data name=\"CUR_RECORD_NO\"><field length=\"10\" type=\"string\"/></data><data name=\"CUR_PAGE_FIRST_NUM_FLAG\"><field length=\"10\" type=\"string\"/></data><data name=\"CUR_PAGE_LAST_NUM_FLAG\"><field length=\"10\" type=\"string\"/></data><data name=\"TOTAL_NUM\"><field length=\"12\" type=\"string\"/></data><data name=\"TOTAL_PAGE_CNT\"><field length=\"12\" type=\"string\"/></data><data name=\"SMZG_FLAG\"><field length=\"10\" type=\"string\"/></data><data name=\"PROD_OPRT_FLAG\"><field length=\"10\" type=\"string\">0</field></data><data name=\"PROD_TYPE\"><field length=\"50\" type=\"string\"/></data><data name=\"PROD_NO\"><field length=\"50\" type=\"string\">STM-HB01</field></data><data name=\"MIN_EXPECTED_YEAR_INCOME_RATE\"><field length=\"50\" type=\"string\"/></data><data name=\"MAX_EXPECTED_YEAR_INCOME_RATE\"><field length=\"64\" type=\"string\"/></data><data name=\"GLOBAL_TYPE\"><field length=\"8\" type=\"string\"/></data><data name=\"GLOBAL_ID\"><field length=\"8\" type=\"string\"/></data><data name=\"ENTR_DATE\"><field length=\"3\" type=\"string\"/></data><data name=\"GLOBAL_TYPE\"><field length=\"30\" type=\"string\"/></data><data name=\"GLOBAL_ID\"><field length=\"32\" type=\"string\"/></data><data name=\"ENTR_DATE\"><field length=\"30\" type=\"string\"/></data><data name=\"INIT_BANK_NO\"><field length=\"12\" type=\"string\"/></data><data name=\"BUSI_TYPE\"><field length=\"10\" type=\"string\"/></data><data name=\"TRAN_SERIAL_NO\"><field length=\"12\" type=\"string\"/></data><data name=\"OPEN_BR_CODE\"><field length=\"12\" type=\"string\"/></data><data name=\"CLNT_TYPE\"><field length=\"12\" type=\"string\"/></data></body></service>";

    public static void main(String[] args) {
        try{
            Socket socket = new Socket();

            socket.setReuseAddress(true);
            socket.setSoTimeout(60000);
            socket.setSoLinger(true, 5);
            socket.setSendBufferSize(32 * 1024);
            socket.setReceiveBufferSize(32 * 1024);
            socket.setTcpNoDelay(true);
            socket.connect(new InetSocketAddress("172.16.31.12", 31044), 10000);

            OutputStream out = null;
            out = new BufferedOutputStream(socket.getOutputStream());
            String xmlStr = msg;
            byte[] xmlbytes = xmlStr.getBytes();
            int length = xmlbytes.length;
            String lengthStr = String.format("%08d", length);
            xmlStr = lengthStr + xmlStr;
            //System.out.println(xmlStr);
            byte[] bytes = xmlStr.getBytes();
            out.write(bytes);
            out.flush();


            InputStream is = null;
            is = new BufferedInputStream(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuffer stringBuffer = new StringBuffer();
            String message = "";
            while ((message = bufferedReader.readLine()) != null) {
                stringBuffer.append(message);
            }
            String xml = stringBuffer.toString();

            System.out.println(xml);
            is.close();
            out.close();
            socket.close();
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
