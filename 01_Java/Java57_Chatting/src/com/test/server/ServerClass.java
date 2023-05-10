package com.test.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerClass {

	public static void main(String[] args) {
		// 기능1
		// - 사용자 접속 대기를 한다.
		// - 사용자 접속에 성공하면 닉네임을 입력 받는다.
		// - 입력받은 닉네임과 생성된 소켓을 객체에 담고 해당 객체를 ArrayList에 담아준다.
		// - 다시 사용자 접속 대기를 한다.
		
		// 기능2
		// - 사용자에게 메세지를 수신 대기한다.
		// - 메세지가 전달되면 이를 받는다.
		// - ArrayList에 있는 모든 사용자에게 메세지르 전달한다.

		new ChattingServer();
	}

}

// 채팅 서버
class ChattingServer {
	
	private ServerSocket server;
	final int SERVER_PORT = 40000;
	private ArrayList<UserClass> userList;
	
	public ChattingServer () {
		try {
			// 사용자 객체를 담을 ArrayList 객체 생성
			userList = new ArrayList<UserClass>();
			// 서버 가동
			server = new ServerSocket(SERVER_PORT);
			
			// 사용자 접속 처리쓰레드 가동
			AccessUserClass accessUserClass = new AccessUserClass();
            accessUserClass.start();
            
        }catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	// 브로드캐스팅 메서드
    public synchronized void broadCastingMethod(String userNickName, String msg) {
        try {
        	// 메세지를 구성한다.
        	String sendMsg = userNickName + " : " + msg;
        	
        	// ArrayList의 객체만큼 반복한다.
        	for(UserClass userClass : userList) {
        		// 현재번째의 사용자의 닉네임을 가져온다.
        		String targetUserNickname = userClass.getUserNickName();
        		
        		// 메세지를 보낸 사람과 같으면 보내주지 않는다.
        		if(userNickName == targetUserNickname) {
                    continue;
                }
        		
                //  메시지를 보낸다.
                userClass.sendMessage(sendMsg);
        	}
        	
        } catch(Exception e) {
        	e.printStackTrace();
        }
    }
	
	
	// 사용자 접속 처리 중첩 클래스
	class AccessUserClass extends Thread {
		public void run() {
			try {
                while(true) {
                	System.out.println("사용자 접속 대기");
                	Socket socket = server.accept();
                	// System.out.println(socket);
                	
                	// 사용자 객체를 생성한다.
                	UserClass userClass = new UserClass(socket);
                	userClass.start();
                	
                	// 사용자 객체를 ArrayList에 담는다.
                	userList.add(userClass);
                }
                
            }catch(Exception e) {
                e.printStackTrace();
            }
		}
	}
	
	// 사용자 관련 중첩 클래스 
	class UserClass extends Thread {
		private String userNickName;
		private Socket socket;
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
        private DataOutputStream dos;
		
		public UserClass(Socket socket) {
			try {
				this.socket = socket;
				is = socket.getInputStream();
				os = socket.getOutputStream();
				dis = new DataInputStream(is);
				dos = new DataOutputStream(os);
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void run() {
			try {
                // 닉네임을 입력 받는다.
				userNickName = dis.readUTF();
				System.out.printf("%s 접속\n", userNickName);
				// 클라이언트에게 환영 메세지를 보낸다.
				dos.writeUTF(userNickName + "님 환영합니다"); 
				// 다른 클라이언트들에게 환영 메세지를 보낸다.
				broadCastingMethod("서버", userNickName + "님이 접속하였습니다");
				
				// 사용자가 보내는 메세지를 수신 대기
				while(true) {
					// 사용자가 보내는 메세지를 받는다.
					String msg = dis.readUTF();
					// 다른 사용자들에게 보낸다.
                    broadCastingMethod(userNickName, msg);
					
				}
				
				
            }catch(Exception e) {
                e.printStackTrace();
            }
		}
		
		public void sendMessage(String msg) {
			try {
                // 클라이언트에게 보낸다.
                dos.writeUTF(msg);
                
            }catch(Exception e) {
                e.printStackTrace();
            }
		}
		
		public String getUserNickName() {
			return userNickName;
		}
	}
}