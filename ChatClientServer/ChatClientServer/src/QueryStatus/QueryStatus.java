package QueryStatus;

public class QueryStatus {
    private static int status;
    private static String msg;

    public static final int ok = 0;
    public static final int nok = -1;

    public QueryStatus () {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        QueryStatus.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        QueryStatus.msg = msg;
    }

}
