import java.io.File
import java.io.FileOutputStream
import java.util.*

class CategoryManagementClass(var scanner : Scanner, val mainClass:MainClass) {

    fun selectCategoryManagementMenu() : Int{
        while(true){
            try {
                println()
                println("1. 카테고리 등록")
                println("2. 카테고리 수정")
                println("3. 카테고리 삭제")
                println("4. 이전")
                print("메뉴를 선택해주세요 : ")
                val managmentNumber = scanner.next().toInt()

                if(managmentNumber !in 0..4){
                    println()
                    println("잘못 입력하였습니다.")
                } else {
                    return managmentNumber
                }
            } catch (e:Exception){
                println()
                println("잘못 입력하였습니다.")
            }
        }
    }

    fun inputCategory(){
        println()
        print("등록할 카테고리 이름을 입력해주세요 : ")
        scanner.nextLine()
        var categoryName = scanner.nextLine()

        val fos = FileOutputStream("${categoryName}.cat")
        fos.flush()
        fos.close()
        println("카테고리 등록 성공!")
    }

    fun updateCategory(){
        println()
        var num = 0
        while (true){
            try {
                print("수정할 카테고리 번호를 입력해주세요 : ")
                num = scanner.next().toInt()

                // 수정할 카테고리의 번호를 잘못 입력하면
                if (num !in 1..mainClass.categoryList.size+1){
                    println("잘못 입력하였습니다.")
                } else {
                    break // 번호를 제대로 입력했다면 while 종료
                }
            } catch (e:Exception){
                println("잘못 입력하였습니다.")
            }
        }

        print("${mainClass.categoryList[num-1]} -> ")
        scanner.nextLine()
        val update = scanner.nextLine()

        val file = File("./${mainClass.categoryList[num-1]}.cat")
        val updateFile = File("./${update}.cat")

        // 카테고리(파일) 이름 변환, boolean 타입으로 반환됨
        val result = file.renameTo(updateFile)

        if (result){
            println("카테고리 이름 변경 완료!")
        } else {
            println("카테고리 이름 변경 실패")
        }
    }

    fun deleteCategory(){
        println()
        var num = 0
        while (true){
            try {
                print("삭제할 카테고리 번호를 입력해주세요 : ")
                num = scanner.next().toInt()

                // 삭제할 카테고리의 번호를 잘못 입력하면
                if (num !in 1..mainClass.categoryList.size+1){
                    println("잘못 입력하였습니다.")
                } else {
                    break // 번호를 제대로 입력했다면 while 종료
                }
            }catch (e:Exception){
                println("잘못 입력하였습니다.")
            }
        }

        val file = File("./${mainClass.categoryList[num-1]}.cat")

        // 카테고리(파일) 이름 변환, boolean 타입으로 반환됨
        val result = file.delete()

        if (result){
            println("카테고리 삭제 완료!")
        } else {
            println("카테고리 삭제 실패")
        }
    }
}

enum class CategoryManagementItem(val itemNumber:Int) {

    // 카테고리 등록
    CATEGORY_INSERT(1),

    // 카테고리 수정
    CATEGORY_UPDATE(2),

    // 카테고리 삭제
    CATEGORY_DELETE(3),

    // 이전
    CATEGORY_BEFORE(4)
}