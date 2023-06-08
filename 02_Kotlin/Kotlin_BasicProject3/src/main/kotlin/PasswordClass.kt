import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class PasswordClass(var scanner : Scanner) {
    fun settingPassword(){

        // 비밀번호를 저장한 파일의 상대경로
        val file = File("./login.pw")

        // 파일이 존재하지 않는다면 비밀번호 초기 설정
        if (!file.exists()){
            while (true){
                println()
                print("설정할 비밀번호를 입력해주세요 : ")
                val passwordFirst = scanner.nextInt()
                print("한번 더 입력해주세요 : ")
                val passwordSecond = scanner.nextInt()

                if (passwordFirst != passwordSecond){
                    println("다시 입력해주세요.")
                    continue
                } else{
                    savePassword(passwordFirst) // 비밀변호 저장
                    break
                }
            }
        }
        checkPassword() // 비밀번호 입력 및 확인
    }

    fun checkPassword(){
        val fis = FileInputStream("login.pw")
        val dis = DataInputStream(fis)

        val checkPassword = dis.readInt()

        dis.close()
        fis.close()

        while (true){
            println()
            print("로그인 하시려면 비밀번호를 입력하세요 : ")
            val inputPassword = scanner.nextInt()

            if (inputPassword != checkPassword){
                println("다시 입력해주세요.")
            } else {
                println("로그인 성공!")
                break
            }
        }
    }

    fun savePassword(password : Int){
        val fos = FileOutputStream("login.pw")
        val dos = DataOutputStream(fos)

        dos.writeInt(password)

        dos.flush()
        dos.close()
        fos.close()

        println("비밀번호 설정 완료!")
    }
}