package model;

public class User {
	
	private String UserPassword;
	private String name;
	private String NRIC;
	private String email;
	private String dateofbirth;
	private String mobile;
	private String securityQuestion;
	private String securityAnswer;
	private int attempts;
	private int serialno;
	
	public User(String refPassword,String refName, String refNric, String refEmail, String refDob, String refMobile, String refSQn, String refSAn,int refAttempts){
		this.UserPassword = refPassword;
		this.name = refName;
		this.NRIC = refNric;
		this.email = refEmail;
		this.dateofbirth = refDob;
		this.mobile = refMobile;
		this.securityQuestion = refSQn;
		this.securityAnswer = refSAn;
		this.attempts = refAttempts;
	}
	
	public String getUserPassword() {
		return UserPassword;
	}
	public void setUserPassword(String UserPassword) {
		this.UserPassword = UserPassword;
	}
	public String getName() {
		return name;
	}
	public void setName(String Inputname) {
		this.name = Inputname;
	}
	public String getNRIC() {
		return NRIC;
	}
	public void setNRIC(String InputnRIC) {
		this.NRIC = InputnRIC;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String Inputemail) {
		this.email = Inputemail;
	}
	public String getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(String Inputdateofbirth) {
		this.dateofbirth = Inputdateofbirth;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String Inputmobile) {
		this.mobile = Inputmobile;
	}
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public String getSecurityAnswer() {
		return securityAnswer;
	}
	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public int getSerialno() {
		return serialno;
	}
	public void setSerialno(int serialno) {
		this.serialno = serialno;
	}

}