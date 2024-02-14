package QueryStatus;

public class QueryStatus {
    private static int status;
    private static String msg;

    public static final int ok = 0;
    public static final int nok = -1;

    public QueryStatus () {

    }

    public static int getStatus() {
        return status;
    }

    public static void setStatus(int status) {
        QueryStatus.status = status;
    }

    public static String getMsg() {
        return msg;
    }

    public static void setMsg(String msg) {
        QueryStatus.msg = msg;
    }

}
