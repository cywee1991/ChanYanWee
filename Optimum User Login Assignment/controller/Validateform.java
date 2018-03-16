package controller;

import java.util.Scanner;
import java.util.regex.Pattern;

import dao.UserDAOImpl;
import model.User;

public class Validateform {
	
	Scanner sc = new Scanner(System.in);
	User refUser = new User("","","","","","","","",0);
	static PageDisplay refDisplay = new PageDisplay();
	static UserDAOImpl refDAO = new UserDAOImpl();
	
	//----------------Start of Validation of Registration List-------------//
	// Validate name for Registration List
	void validateName() {
		String name = sc.nextLine();
		String nameRegex = "(([a-zA-Z]+\\s)*[a-zA-Z]*)";
		
		Pattern patternname = Pattern.compile(nameRegex);
		boolean verify = patternname.matcher(name).matches();
		
		if(verify==true && !name.equals("")) {
			refUser.setName(name);
		}
		
		else {
			System.out.println("Invalid Name. Kindly re-enter name");
			validateName();
		}
		
	}
	
	// Validate name for Registration List
	void validateIC() {
		String IC = sc.nextLine();
		String ICRegex = "[a-zA-Z][0-9]{7}[a-zA-Z]";
		Pattern patternIC = Pattern.compile(ICRegex);
		boolean verify = patternIC.matcher(IC).matches();
	
		if(verify==true) {
			refUser.setNRIC(IC);
		}
		
		else {
			System.out.println("Invalid IC. Kindly re-enter IC");
			validateIC();
		}
	}// End Of Validation
	
	// Validate Date Of Birth for Registration List
	void validateDOB() {
		String date = sc.nextLine();
		String dateOfBirthRegex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
		Pattern patterndate = Pattern.compile(dateOfBirthRegex);
		boolean verify = patterndate.matcher(date).matches();
		
		if(verify==true) {
			refUser.setDateofbirth(date);
		}
		
		else {
				System.out.println("Invalid Date of Birth. Kindly re-enter date");
				validateDOB();
			}
		}// End Of Validation
	
	// Validate email for Registration List
	void validateemail() {
		String email = sc.nextLine();
		String emailRegex = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		Pattern patternemail = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
		boolean verify = patternemail.matcher(email).matches();
		
		if(verify==true) {
			refUser.setEmail(email);
		}
		
		else {
			System.out.println("Invalid Email. Kindly re-enter email");
			validateemail();
		}
	} // End of Validation
	
	// Validate mobile for Registration List
	void validatemobile() {
		String mobile = sc.next();
		String mobileRegex = "[0-9]{8}";
		Pattern patternmobile = Pattern.compile(mobileRegex);
		boolean verify = patternmobile.matcher(mobile).matches();
		
		if(verify==true) {
			refUser.setMobile(mobile);
		}
		
		else {
			System.out.println("Invalid Mobile no. Kindly re-enter mobile");
			validatemobile();
		}
	}// End of Validation
	//----------------End of Validation of Registration List-------------//
	
	// Generate TempPassword for User
	void generateTempPassword() {
		String temppw;
		temppw = (refUser.getNRIC().substring(1,5) + refUser.getMobile().substring(0,4));
		refUser.setUserPassword(temppw);
	} // End Of Generation
	
	// Show User Particulars Last time for Confirmation
	void showdetails() {
		System.out.println("Last Confirmation for your particulars");
		System.out.println("Name :"+ refUser.getName());
		System.out.println("IC :"+ refUser.getNRIC());
		System.out.println("Date of Birth :"+ refUser.getDateofbirth());
		System.out.println("Email :"+ refUser.getEmail());
		System.out.println("Mobile :"+ refUser.getMobile());
		confirmdetails();
	}
	
	void confirmdetails() {
		System.out.println("Do you wish to proceed? Yes/No");
		String choice = sc.next();
		
		switch (choice) {
		
		case ("Yes") :
			generateTempPassword();
		    refDAO.updatetable(refUser);
		    refDisplay.getAdminchoice(refUser);
		    break;
			
			
		case ("No") :
			PageDisplay.getRegistrationPage();
			break;
			
		default:
			System.out.println("Invalid Input. Kindly re-enter your choice");
			confirmdetails();
			break;
			
		}
		
	}
	// End of Confirmation-----Values inserted into SQL database
	
	// Validate Security Question Answer. Make sure it is single word
	void validateanswer(User user) {
		this.refUser = user;
		System.out.println("Enter your Answer");
		String answer = sc.next();
		String answerRegex ="[a-zA-Z]*";
		Pattern patternanswer = Pattern.compile(answerRegex);
		boolean verify = patternanswer.matcher(answer).matches();
		if(verify==true) {
			refUser.setSecurityAnswer(answer);
			refDAO.updateSecurity(refUser);
		}
		
		else {
			System.out.println("Invalid Answer. Kindly re-enter answer");
			validateanswer(refUser);
		}
	} // End of Validation
	
	// Validate Temp Password for First Login
		void validateTempPassword() {
			String temppw= sc.next();
			if (temppw.equals(refUser.getUserPassword())) {
				refDisplay.getNewPasswordDisplay(refUser);
			}
			else {
				refDisplay.getTempPasswordDisplay(refUser);
			}
		} // End of Validation
	
	//Validate Re-Type Password for First Login
	void validateReTypePassword(User user) {
		this.refUser = user;
		String confirmpassword=sc.next();
		if (confirmpassword.equals(refUser.getUserPassword())) {
			refUser.setUserPassword(confirmpassword);
			refDAO.setNewPassword(refUser);
		}
		else {
			System.out.println("Password confirmation invalid!");
			refDisplay.getNewPasswordDisplay(refUser);
		}
		
	} // End of Validation

}
