//Java:

import java.util.Scanner; // 调用Scanner类

import static java.lang.System.exit;

public class Demo {

    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        menuView();
    }


    // -------------------- 数据设计 --------------------
/*
    棋盘:
        map[i][j]表示坐标(i,j)的值
        0表示空地
        1表示黑子
        2表示白子
    如: map[3][6] = 1  表示(3,6)的位置是黑子
*/
    public static int map[][] = new int[19][19];

    // 表示当前回合数  偶数表示黑棋落子  奇数表示白棋落子
// 如: flag = 20 表示当前是第[20]次落子  由黑方落子
    public static int flag;
// -------------------- 数据设计 --------------------


    // -------------------- service --------------------
/*
    负责人: 车厘子
    功能: 初始化游戏数据
        将棋盘的值初始化为0
        当前回合设为黑棋(flag设为0)
    参数: void
    返回值: void
*/
    public static void init() {
        //嵌套for循环使每一个map值为0
        for (int i = 0; i < 19; i++) { //嵌套循环给map所有值赋 0
            for (int j = 0; j < 19; j++) {
                map[i][j] = 0;
            }
        }
        flag = 0; // 开始为黑方回合

    }

    /*
        *难点1
        负责人: 车厘子
        功能: 根据传入的坐标(map对应位置)和flag值 判断落点后是否获胜
        参数:
            x: 当前回合落子的x坐标
            y: 当前回合落子的y坐标
        返回值:
            0表示没有获胜
            1表示黑子胜利
            2表示白子胜利
    */
    public static int isWin(int x, int y) {
        /*
        我的思路是去判断周围八个方向延伸出去有几个连续的同一个颜色的棋子.例如左上的棋子加个右下的棋子大于4就算成功(算上当前下的棋子为 5, 则胜利)
         */
        int chessPieces = (flag % 2 == 0) ? 1 : 2; //根据当前回合数来判断下的是黑子还是白子
        int num1 = checkDirection(x, y, -1, -1, chessPieces);// 左上
        int num2 = checkDirection(x, y, 0, -1, chessPieces);// 上
        int num3 = checkDirection(x, y, 1, -1, chessPieces);// 右上
        int num4 = checkDirection(x, y, 1, 0, chessPieces);// 右
        int num5 = checkDirection(x, y, 1, 1, chessPieces);// 右下
        int num6 = checkDirection(x, y, 0, 1, chessPieces);// 下
        int num7 = checkDirection(x, y, -1, 1, chessPieces);// 左下
        int num8 = checkDirection(x, y, -1, 0, chessPieces);// 左
        if (num1 + num5 >= 4 || num2 + num6 >= 4 || num3 + num7 >= 4 || num4 + num8 >= 4) { // 4个方向加起来有一个大于等于4,表明有人胜利
            if (chessPieces == 1) { // chessPieces == 1 说明是黑棋胜利
                return 1;
            } else {// chessPieces != 1,则 chessPieces == 2 说明白棋胜利
                return 2;
            }
        } else { // 没有任何一个方向大于等于4, 没有胜利.
            return 0;
        }
    }

    //自定义的函数用于判断相应的方向, 有几个同色的棋子
    public static int checkDirection(int x, int y, int dirX, int dirY, int chessPieces) {
        // x,y用于记录当前棋子下的位置; dirX,dirY用来记录判断的方向; chessPieces用来判断当前棋子是什么颜色的
        int i = 1;
        int num = 0;
        while (x + dirX * i >= 0 && x + dirX * i < 19 && y + dirY * i >= 0 && y + dirY * i < 19
                && map[x + dirX * i][y + dirY * i] == chessPieces) { //先判断是否越界, 然后再判断 map[x + dirX * i][y + dirY * i] == chessPieces
            num++;
            i++;
        }
        return num;
    }

    /*
        负责人: 车厘子
        功能: 在指定位置落子
            如果map[x][y]是空地 则修改map[x][y]的值:改为相应颜色(flag对应颜色)        否则不操作
        参数:
            x: 当前回合落子的x坐标
            y: 当前回合落子的y坐标
        返回值:
            0表示落子失败 (棋盘已经有子)
            1表示落子成功

    */
    public static int playerMove(int x, int y) {
        int chessPieces = (flag % 2 == 0) ? 1 : 2; //根据当前回合数来判断下的是黑子还是白子

        if (x >= 0 && x < 19 && y >= 0 && y < 19 && map[x][y] == 0) {
            map[x][y] = chessPieces;
            return 1;
        } else {
            return 0;
        }
    }
// -------------------- service --------------------


    // -------------------- view --------------------
/*
    功能: 展示选项, 玩家可以在这里选择进入游戏, 进入设置或退出游戏
        while(1){
            1. 展示选项
            2. 用户输入
            3. 根据输入进行对应处理
                进入游戏: 调用游戏界面函数gameView();
                进入设置: 打印敬请期待... 重新循环
                退出游戏: 调用exit(0);
        }
*/
    public static void menuView() {
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("1.进入游戏");
            System.out.println("2.进入设置");
            System.out.println("3.退出游戏");
            System.out.print("请输入你的操作:");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    init();
                    gameView();
                    break;
                case 2:
                    System.out.println("敬请期待... ");
                    break;
                case 3:
                    exit(0);
            }
        } while (true);
    }

    /*
        负责人: 车厘子
        功能: 根据map数组 打印游戏棋盘
        参数: void
        返回值: void
    */
    public static void gameView_ShowMap() {
        System.out.print("    ");
        for (int j = 0; j < 19; j++) {
            System.out.printf("%3d", (j + 1)); // 打印列标号
        }
        System.out.println();
        for (int i = 0; i < 19; i++) {
            System.out.printf("第%2d行:", (i + 1));

            for (int j = 0; j < 19; j++) {
                System.out.print(map[i][j] + "  ");
            }
            System.out.println();
        }
    }

    /*
        负责人: 车厘子
        功能: 根据flag的值  打印游戏胜利界面  用户可以按任意键回到主菜单
        参数: void
        返回值: void
    */
    public static void winView() {
        System.out.println("恭喜你胜利啦,点击任意键回主菜单...");
        try {
            System.in.read();  // 读取任意键（会阻塞直到用户按下回车）
            scan.nextLine();    // 清除输入缓冲区残留的换行符
        } catch (Exception e) {
            e.printStackTrace();
        }

        menuView();  // 返回主菜单
    }

    /*
        *难点2
        负责人: 车厘子
        功能: 游戏界面整合
            初始化游戏数据(调用函数init())
            while(1){
                打印游戏界面(调用函数gameView_ShowMap())
                接收玩家坐标输入

                落子(调用落子函数playerMove())
                    (如果落子失败 重新开始循环)

                判断游戏是否胜利(调用胜利判断函数isWin())
                    (如果游戏胜利 调用胜利界面函数 然后结束当前界面)
                切换玩家(修改flag值)
            }
        参数: void
        返回值: void
    */
    public static void gameView() {
        Scanner scan = new Scanner(System.in);
        int x;
        int y;
        while (true) {
            gameView_ShowMap();// 先打印棋盘
            if (flag % 2 == 0) {
                System.out.println("当前为黑方回合,请输入你要下的位置(格式:第几行 第几列;最开始为第1行):");
            } else {
                System.out.println("当前为白方回合,请输入你要下的位置(格式:第几行 第几列;最开始为第1行):");
            }
            x = scan.nextInt();//读入需要落子的行坐标
            y = scan.nextInt();//读入需要落子的列坐标

            if (playerMove(x - 1, y - 1) == 0) {
                // 调用落子函数,返回 0就失败就进入下一次循环并让当前回合的玩家再次落子
                System.out.println("落子失败");
                continue;
            } else {
                // 如果返回 1则成功,判断是否有玩家胜利.如果有玩家胜利打印胜利界面
                switch (isWin(x - 1, y - 1)) {
                    case 1:
                        gameView_ShowMap();
                        System.out.println("恭喜黑方取得胜利");
                        winView();
                        return;// 使用return直接结束函数gameView()
                    case 2:
                        gameView_ShowMap();
                        System.out.println("恭喜白方取得胜利");
                        winView();
                        return;// 使用return直接结束函数gameView()
                    case 0:
                        break;
                }
            }
            flag++;//当前回合结束让 flag++ ,进入下一回合
        }

    }
// -------------------- view --------------------
}