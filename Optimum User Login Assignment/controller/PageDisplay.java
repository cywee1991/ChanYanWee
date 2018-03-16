package controller;

import java.util.Scanner;

import dao.UserDAOImpl;
import model.User;

public class PageDisplay {
	UserDAOImpl refDAO = new UserDAOImpl();
	static Validateform refValidate = new Validateform();
	User refUser = new User("","","","","","","","",0);
	static Scanner sc = new Scanner(System.in);
	// Main Menu Display
	public void getMainChoice() {
		System.out.println("1. Login");
		System.out.println("2. Forget Password");
		
		String choice = sc.next();
		switch(choice) {
		
		case "1" : 
			getLoginPage1(refUser);
			break;
		
		case "2" :
			getForgetPasswordPage1(refUser);
			break;
		default:
			System.out.println("Invalid Input! Kindly Re-Enter choice.");
			getMainChoice();
			break;
		}
	} // End of Main Menu Display
	
	// LoginPage
	public void getLoginPage1(User user) {
		
		this.refUser = user;
		System.out.println("Login ID (Email) : ");
		String ID = sc.next();
		refUser.setEmail(ID);
		refDAO.validateUserLogin(refUser);
	}// End of Login Page
	
	// Enter Password Page
	public void getPasswordPage1(User user) {
		this.refUser = user;
		System.out.println("Password: ");
		String password = sc.next();
		refUser.setUserPassword(password);
		refDAO.validateUserPassword(refUser);	
	} // End of Enter Password Page
	
	//ForgetPassword Page
	public void getForgetPasswordPage1(User user) {
		this.refUser = user;
		System.out.println("Login ID (Email) : ");
		String ID = sc.next();
		refUser.setEmail(ID);
		refDAO.validateForgetPasswordLogin(refUser);
	}// End of Forget Password Page
	
	// Forget Password Page (Security Question)
	public void enterSecurityQuestion(User user) {
		this.refUser = user;
		System.out.println("Enter Security Question choice");
		System.out.println("A: What is your favourite colour ?" );
		System.out.println("B: What is your childhood superhero ?");
		System.out.println("C: What is your favourite pet ?");
		String question =sc.next();
		refUser.setSecurityQuestion(question);
		refDAO.validateSecurityQuestion(refUser);
	} // End of Forget Password Page (Security Question)
	
	// Forget Password Page (Security Answer)
	public void enterSecurityAnswer(User user) {
		this.refUser = user;
		System.out.println("Enter Security Answer");
		String answer =sc.next();
		refUser.setSecurityAnswer(answer);
		refDAO.validateSecurityAnswer(refUser);
	} // Forget Password Page (Security Answer)
	
	
	// Admin Menu Display
	public void getAdminchoice(User user) {
		this.refUser = user;
		System.out.println("1. Register New User");
		System.out.println("2. View User List");
		System.out.println("3. Logout");
		
		String choice = sc.next();
		System.out.println(" ");
		switch(choice) {
		
		case "1" : 
			getRegistrationPage();
			break;
		
		case "2" :
			System.out.println("DataBase List");
			System.out.println(" ");
			refDAO.showUserDetails(refUser);
			System.out.println(" ");
			getViewUserListChoice(refUser);
			break;
			
		case "3" :
			logoutdisplay();
			
			break;
		}
	} // End of Admin Display
	
	// View User List Choice Display
	public void getViewUserListChoice(User user) {
		this.refUser = user;
		System.out.println("Enter Available Options below");
		System.out.println("1. Unlock Account");
		System.out.println("2. Back");
		
		int choice = sc.nextInt();
		switch (choice) { 
		case(1):
			System.out.println("Enter serial no. to Unlock account");
			int unlock=sc.nextInt();
			refUser.setSerialno(unlock);
			refDAO.selectUserUnLockStatus(refUser);
			getAdminchoice(refUser);
			break;
		case(2):
			getAdminchoice(refUser);
			break;
		default:
			System.out.println("Invalid choice! Kindly Re-Enter choice");
			getViewUserListChoice(refUser);
			break;
		}
	}// End of Display
	
	
	// Registration Page
	public static void getRegistrationPage() {
		
		System.out.println("Enter Name: ");
		refValidate.validateName();         // Name validation
		
		System.out.println("Enter NRIC: ");
		refValidate.validateIC();           // IC validation
		
		System.out.println("Enter DOB: ");
		refValidate.validateDOB();          // Date validation
		
		System.out.println("Enter Email: ");
		refValidate.validateemail();        // Email validation
		
		System.out.println("Enter mobile no.:");
		refValidate.validatemobile();       // Mobile validation
		
		refValidate.showdetails();          // Confirm details
	} // End of Registration Page
	
	
	// Logout Loop Display
	public void logoutdisplay() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you wish to Logout: Y/N");
		String choice = sc.next();
		
		if (choice.equals("Y")) {
			getMainChoice();
		}
		else if (choice.equals("N")) {
			logoutdisplay();
		}
		
		else {
			System.out.println("Invalid Input");
			logoutdisplay();
		}
	} // End of Logout Loop

	
	//Security Question Display
	public void securityQuestionDisplay(User user) {
		this.refUser = user;
		System.out.println("Choose your Security Question:");
		System.out.println("A: What is your favourite colour ?" );
		System.out.println("B: What is your childhood superhero ?");
		System.out.println("C: What is your favourite pet ?");
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose your Security Question A,B or C");
		String questionchoice = sc.next();
		
		switch (questionchoice) {
		
		case "A" : 
		case "a" :
				refUser.setSecurityQuestion(questionchoice);
				refValidate.validateanswer(refUser);
				break;
		case "B" :
		case "b" :
				refUser.setSecurityQuestion(questionchoice);
				refValidate.validateanswer(refUser);
				break;
		case "C" :
		case "c" :
				refUser.setSecurityQuestion(questionchoice);
				refValidate.validateanswer(refUser);
				break;
		default :
				System.out.println("Invalid Choice. Kind Re-Enter");
				securityQuestionDisplay(refUser);
		
		}
	} // End of Security Question 
	
	
	//Wrong Password Loop
	public void getwrongpasswordchoice(User user) {
		this.refUser = user;
		System.out.println("Invalid Login Password! Do you wish to go to Forgetpassword page? Yes/No ");
		Scanner sc = new Scanner(System.in);
		String choice = sc.next();
		switch(choice) {
		
		case "Yes" : 
			getForgetPasswordPage1(refUser);
			break;
		
		case "No" :
			getPasswordPage1(refUser);
			break;
			
		default :
			System.out.println("Invalid choice. Kindly Re-enter again.");
			getwrongpasswordchoice(refUser);
			break;
		}
	} // End of Wrong Password Loop
	
	// First Login to Enter Temp Password and Finally Set Password Flow
	public void getTempPasswordDisplay(User user) {
		this.refUser = user;
		System.out.println("Enter Temp Password:");
		String temppw= sc.next();
		refUser.setUserPassword(temppw);
		refDAO.validateTempPassword(refUser);
	}
	public void getNewPasswordDisplay(User user) {
		this.refUser = user;
		System.out.println("Enter New Password:");
		String newpw = sc.next();
		refUser.setUserPassword(newpw);
		getReTypePasswordDisplay(refUser);
	}
	public void getReTypePasswordDisplay(User user) {
		this.refUser = user;
		System.out.println("Retype new Password:");
		refValidate.validateReTypePassword(refUser);
	}
	// End of Flow
	
	
}