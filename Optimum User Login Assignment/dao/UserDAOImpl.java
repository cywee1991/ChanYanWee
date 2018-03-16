package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import connection.DatabaseConnection;
import controller.PageDisplay;
import model.User;

public class UserDAOImpl {
	private Connection conn = DatabaseConnection.getConnection();
	
	User refUser = new User("","","","","","","","",0);
	
	public void LoginDAOImplementation() {
		conn=DatabaseConnection.getConnection();
	}
	
	// Validate UserID Login 
	public void validateUserLogin(User user) {
		this.refUser = user;
		try {
			PageDisplay refDisplay = new PageDisplay();
			String query = "SELECT * FROM FieldsOfDatabase where EmailAddress = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1,refUser.getEmail());
	        ResultSet rs = preparedStatement.executeQuery();
	        if(rs.next()) {
	        	refDisplay.getPasswordPage1(refUser);
	        	
	        	}
	        
	        	
	        		
	        else {
	        	System.out.println("Invalid Login ID! Kindly Re-Enter Login ID. Thank you");
	        	refDisplay.getLoginPage1(refUser);
	        }
	        	}catch (Exception e) {
			e.printStackTrace();
		}
		
	} // End of Validate UserID Login
	
	// Validate Forget Password Login 
		public void validateForgetPasswordLogin(User user) {
			this.refUser = user;
			try {
				PageDisplay refDisplay = new PageDisplay();
				String query = "SELECT * FROM FieldsOfDatabase where EmailAddress = ?";
				PreparedStatement preparedStatement = conn.prepareStatement(query);
				preparedStatement.setString(1,refUser.getEmail());
		        ResultSet rs = preparedStatement.executeQuery();
		        if(rs.next()) {
		        	refDisplay.enterSecurityQuestion(refUser);
		        	}
		        else {
		        	System.out.println("Invalid Login ID! Kindly Re-Enter Login ID. Thank you");
		        	refDisplay.getLoginPage1(refUser);
		        }
		        	}catch (Exception e) {
				e.printStackTrace();
			}
			
		} // End of Validate Forget Password Login
		
		// Validate Security Question 
				public void validateSecurityQuestion(User user) {
					this.refUser = user;
					try {
						PageDisplay refDisplay = new PageDisplay();
						String query = "SELECT * FROM FieldsOfDatabase where SecurityQuestion = ?";
						PreparedStatement preparedStatement = conn.prepareStatement(query);
						preparedStatement.setString(1,refUser.getSecurityQuestion());
				        ResultSet rs = preparedStatement.executeQuery();
				        if(rs.next()) {
				        	refDisplay.enterSecurityAnswer(refUser);
				        	
				        }	
				        	}catch (Exception e) {
						e.printStackTrace();
					}
					
				} // End of Validate Security Question
				
				// Validate Security Answer 
				public void validateSecurityAnswer(User user) {
					this.refUser = user;
					try {
						PageDisplay refDisplay = new PageDisplay();
						String query = "SELECT * FROM FieldsOfDatabase where SecurityAnswer = ?";
						PreparedStatement preparedStatement = conn.prepareStatement(query);
						preparedStatement.setString(1,refUser.getSecurityAnswer());
				        ResultSet rs = preparedStatement.executeQuery();
				        if(rs.next()) {
				        	refDisplay.getNewPasswordDisplay(refUser);
				        	
				        }	
				        	}catch (Exception e) {
						e.printStackTrace();
					}
					
				} // End of Validate Security Answer
		
		
	
	// Validate Password Login
	public void validateUserPassword(User user) {
		this.refUser = user;
		try {
			PageDisplay refDisplay = new PageDisplay();
			String query = "SELECT * FROM FieldsOfDatabase where Password = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1,refUser.getUserPassword());
			ResultSet rs = preparedStatement.executeQuery();
			
			
			boolean checker = false;
			while(!checker) {
			if(rs.next()) {
				checker = true;
				String name = rs.getString("Name");
				refUser.setName(name);
				String role = rs.getString("Role");
				String status = rs.getString("Status");
				boolean firstlogin = rs.getBoolean("FirstLogin");
				int serialno = rs.getInt("SerialNo");
				
				//condition for Login of Admin| Assumption: Admin will never forget his password
				if(role.equalsIgnoreCase("admin")&& checker==true && serialno==1) {
					
					System.out.println("Welcome Admin!"+" "+refUser.getName());
					refDisplay.getAdminchoice(refUser);
				}
				// condition for Login of User for First Login | Assumption User will never forget Temp password
				else if(firstlogin==false && checker==true){
					System.out.println("Welcome"+" "+ refUser.getName());
					refDisplay.getTempPasswordDisplay(refUser);
				}
				// condition for Login of User for Second Login 
				else if(status.equalsIgnoreCase("unlock")&& checker==true) {
					System.out.println("Welcome"+" "+ refUser.getName());
					resetUpdateAttempts(refUser);
					refDisplay.logoutdisplay();
				}			
				// condition for Login of User when Account is Locked
				else {
					System.out.println("Account Locked. Please Contact your Adminstrator to unlock your account");
					//refDisplay.getLoginPage1(refUser);
				}
			}
			// condition of Wrong attempts of password for User
			else {
				if((refUser.getAttempts()+1)<3) {
				int numberofattempts = refUser.getAttempts();
				refUser.setAttempts(numberofattempts+1);
				setUpdateAttempts(refUser);
				
				}
				else {
					setLockStatus(refUser);
					refDisplay.getLoginPage1(refUser);
				}	
			}	
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	} // End of Validate Password Login
	
	
	// Set Update Attempts when password wrong
	public void setUpdateAttempts(User user) {
		this.refUser = user;
		PageDisplay refDisplay = new PageDisplay();
		try {
			String query="UPDATE FieldsOfDatabase set NoofAttempts = ? where EmailAddress = \'"+refUser.getEmail()+"\'";
			PreparedStatement preparedStatement = conn.prepareStatement(query);	
			preparedStatement.setInt(1, refUser.getAttempts());
			preparedStatement.executeUpdate();
			System.out.println("Wrong Attempts:"+" "+ refUser.getAttempts()+"!"+" "+ "Warning 3 Wrong Attempts will result in Locking of account" );
			refDisplay.getwrongpasswordchoice(refUser);	
			preparedStatement.close();
			
		}catch (Exception e) {
		e.printStackTrace();
		}
	}
	// End of Update Attempts
	
	// Set Reset Attempts when password 
		public void resetUpdateAttempts(User user) {
			this.refUser = user;
			try {
				String query="UPDATE FieldsOfDatabase set NoofAttempts = ? where EmailAddress = \'"+refUser.getEmail()+"\'";
				PreparedStatement preparedStatement = conn.prepareStatement(query);	
				preparedStatement.setInt(1, 0);
				preparedStatement.executeUpdate();	
				preparedStatement.close();
				
			}catch (Exception e) {
			e.printStackTrace();
			}
		}
		// End of Update Attempts
	
	// Set Lock Status when password attempts wrong for 3 times
	public void setLockStatus (User user) {
		this.refUser = user;
		PageDisplay refDisplay = new PageDisplay();
		try {
			String query="UPDATE FieldsOfDatabase set Status = ? where EmailAddress = \'"+refUser.getEmail()+"\'";
			PreparedStatement preparedStatement = conn.prepareStatement(query);	
			preparedStatement.setString(1, "lock");
			preparedStatement.executeUpdate();
			System.out.println("Your account is locked. Please contact your Adminstrator to unlock your account");
			refDisplay.getLoginPage1(refUser);
			preparedStatement.close();
			
		}catch (Exception e) {
		e.printStackTrace();
		}
	} // End of Set Lock Status
	
	
	// Option for user to unlock Locked accounts for user ( reset status to lock and attempts to 0)
	public void selectUserUnLockStatus (User user) {
		this.refUser = user;
		PageDisplay refDisplay = new PageDisplay();
		try {
			String query="UPDATE FieldsOfDatabase SET Status=?,NoofAttempts=? where SerialNo= \'"+refUser.getSerialno()+"\' ";
			PreparedStatement preparedStatement = conn.prepareStatement(query);	
			preparedStatement.setString(1, "unlock");
			preparedStatement.setInt(2, 0);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
		}catch (Exception e) {
		e.printStackTrace();
		}
	}// End of Option for user to unlock
	
	
	// Insert table for registration by User
	public void updatetable(User user) {
		this.refUser = user;
		try {
			String query = "Insert into FieldsOfDatabase(Name,NRIC,DOB,EmailAddress,Mobile,Password,Role,FirstLogin,Status,NoofAttempts) values(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getName());
			preparedStatement.setString(2, refUser.getNRIC());
			preparedStatement.setString(3, refUser.getDateofbirth());
			preparedStatement.setString(4, refUser.getEmail());
			preparedStatement.setString(5, refUser.getMobile());
			preparedStatement.setString(6, refUser.getUserPassword());
			preparedStatement.setString(7, "user");
			preparedStatement.setBoolean(8, false);
			preparedStatement.setString(9, "unlock");
			preparedStatement.setInt(10, 0);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			sendTempPassword(user.getName(),user.getUserPassword(),user.getEmail());
			System.out.println("Registration successful.\n");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}// End of Insert table for registration by User
	
	// Validation of Temp Password
	public void validateTempPassword(User user) {
		this.refUser = user;
		try {
			PageDisplay refDisplay = new PageDisplay();
			String query = "SELECT * FROM FieldsOfDatabase where Password = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1,refUser.getUserPassword());
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()) {	
				    String password = rs.getString("Password");
				    
				    if(password.equals(refUser.getUserPassword())){
					refDisplay.getNewPasswordDisplay(refUser);
			}
				
			}
			else {
				System.out.println("Invalid TempPassword! Please Re-Enter");
				refDisplay.getTempPasswordDisplay(refUser);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	} // End of Valdiation for Temp password
	
	
	// Set New Password for First Login
	public void setNewPassword(User user) {
		this.refUser = user;
		try {
			PageDisplay refDisplay = new PageDisplay();
			String query="UPDATE FieldsOfDatabase set Password = ? where EmailAddress = \'"+refUser.getEmail()+"\'";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1,refUser.getUserPassword());
			preparedStatement.executeUpdate();
			refDisplay.securityQuestionDisplay(refUser);
			preparedStatement.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	} // End of Set New Password
	
	// Set security question and security answer for First Login by User
	public void updateSecurity(User user) {
		this.refUser = user;
		try {
			PageDisplay refDisplay = new PageDisplay();
			String query="UPDATE FieldsOfDatabase set SecurityQuestion= ?, SecurityAnswer= ? , FirstLogin= ? where EmailAddress = \'"+refUser.getEmail()+"\'";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, refUser.getSecurityQuestion());
			preparedStatement.setString(2, refUser.getSecurityAnswer());
			preparedStatement.setBoolean(3, true);
			preparedStatement.executeUpdate();
			refDisplay.getLoginPage1(refUser);
			preparedStatement.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}// End of set security question and answer
	
	public void showUserDetails(User user) {
		this.refUser = user;
		try {
			String query = "Select * from FieldsOfDatabase";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {	
			    String showserialno = rs.getString("SerialNo");
			    String displayname = rs.getString("Name");
			    String displayNRIC = rs.getString("NRIC");
			    String displayEmail = rs.getString("EmailAddress");
			    String displayMobile = rs.getString("Mobile");
			    String displayStatus = rs.getString("Status");
			    int displayAttempts = rs.getInt("NoofAttempts");
			    
			    System.out.println(showserialno+"\t "+displayname+"\t "+displayNRIC
			    		+"\t "+displayEmail+"\t "+displayMobile+"\t "+displayStatus+"\t "+displayAttempts);     
		}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		// Send Temp password to User
		public void sendTempPassword(String name, String password, String email) {
			
			String to = email;
			String from = "optimum.batch5@gmail.com";
			String emailPass = "Optimum2018";
			
			//Getting session object
				Properties props = System.getProperties();
					props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
					props.put("mail.smtp.socketFactory.port", "465"); // SSL Port
					props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
					props.put("mail.smtp.auth", "true"); // Enabling SMTP Authentication
					props.put("mail.smtp.port", "465"); // SMTP Port
					
					Authenticator auth = new Authenticator(){
						protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from,emailPass);
				}
			};
			Session session = Session.getDefaultInstance(props, auth); 
				//compose the message
			try {
				MimeMessage msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(from));
				msg.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
				msg.setSubject("Temp password");
				msg.setText("Hello " + name + "! The following is your temporary password: " + password);
				
				//Send message
				Transport.send(msg);
				System.out.println("Temporary password has been sent to " + email + ".");
				
			}catch(Exception e) {
				System.out.println(e);
			}
		} // End of Send Temp password to User
	}



	
	


