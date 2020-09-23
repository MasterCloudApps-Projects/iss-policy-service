package es.urjc.code.policy.exception;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 4831389859161854256L;

	protected static final Object[] EMPTY_ARGS = new Object[0];

    private String code;
    private Object[] args;

    
    public String getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

	public BusinessException(String code) {
        super(code);
        this.code = code;
        this.args = EMPTY_ARGS;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.args = EMPTY_ARGS;
    }

    public BusinessException(String code, String message, Object ... args) {
        super(message);
        this.code = code;
        this.args = args;
    }

    public BusinessException(String code, Object ... args) {
        super(code);
        this.code = code;
        this.args = args;
    }
}
