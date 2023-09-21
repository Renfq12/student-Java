
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lanyi
 */
public class final_01 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here

        //讀取上次紀錄(存摺文字檔)
        //日期判斷(每日登入獎勵)
        //讀取牌庫
        //莊家(隨機產生莊家手牌)        
        //玩家(互動式產生玩家手牌)
        //點陣圖(文字檔資料庫)輸出
        //勝利判斷       
        //結算今日結果，存進存摺文字檔(紀錄玩家名稱)，一個玩家一個文字檔
        String bankbook = "bankbook.txt";
        File fbb = new File(bankbook);
        Scanner scf = new Scanner(fbb, "UTF-8");
        Scanner sc = new Scanner(System.in);
        FileWriter fw = new FileWriter(fbb, true);

        int[] originaldata = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52};
        int[] data = new int[53];
        for (int i = 0; i < 52; i++) {
            data[i + 1] = originaldata[i];
        }
        List userinfoList = new ArrayList();
        int money;
        int bet = 100;
        int[] playerHC = new int[5];//玩家手牌
        int[] dealerHC = new int[5];//莊家手牌
        int[] playerHP = new int[5];
        int dealerPt = 0;
        int playerPt = 0;
        int playerPt2 = 0;
        int point = 0;
        int random = 0;
        int a, b;
        int win = 0;
        boolean flag = false;
        boolean leave = false;

        System.out.println("歡迎來到 Blackjack 21\n請輸入您的名字");
        String username = sc.nextLine();

        while (scf.hasNextLine()) {
            String info = scf.nextLine();//每讀到一列資料就加到List中
            if (info.split("#")[0].equals(username)) {
                userinfoList.add(info);
            }
        }
        if (userinfoList.size() == 0) {
            userinfoList.add(username + "#0#0#1000");
        }
        money = Integer.parseInt(userinfoList.get(userinfoList.size() - 1).toString().split("#")[3]);
        money = odate(money, username);
        System.out.println("您目前的金幣數：" + money);

        System.out.println("請問是否進入遊戲(Y/N)");
        String start = sc.nextLine();
        if (start.toUpperCase().equals("Y")) {
            money = money - bet;
            int i = 0;
            int ii = 0;
            do {//玩家發牌

                System.out.println("round " + (i + 1) + "\n" + "您的牌面");

                do {//產生不重複的隨機數
                    random = (int) Math.floor(Math.random() * 52 + 1);
                } while (data[random] == 0);

                playerHC[i] = data[random];
                data[random] = 0;

                for (int p = 0; p < playerHC.length; p++) {
                    if (playerHC[p] != 0) {
                        a = playerHC[p] / 13;
                        if (playerHC[p] % 13 == 0) {
                            a--;
                        }
                        if (playerHC[p] % 13 == 0) {
                            b = 13;
                        } else {
                            b = playerHC[p] % 13;
                        }
                        printcard(a, b);
                    }
                }
                System.out.println();

                //點數計算
                if (playerHC[i] % 13 == 0 || playerHC[i] % 13 >= 10) {
                    point = 10;
                } else if (playerHC[i] % 13 == 1) {
                    point = 1;
                } else {
                    point = playerHC[i] % 13;
                }
                playerPt = playerPt + point;

                if (playerHC[i] % 13 == 0 || playerHC[i] % 13 >= 10) {
                    point = 10;
                } else if (playerHC[i] % 13 == 1) {
                    point = 11;
                } else {
                    point = playerHC[i] % 13;
                }
                playerPt2 = playerPt2 + point;
                if (playerPt == playerPt2) {
                    System.out.println("您目前的點數和為：" + playerPt);
                } else {
                    System.out.println("您目前的點數和為：" + playerPt + " or " + playerPt2);
                }
                //前兩次秀出莊家的牌
                if (i < 2) {
                    System.out.println("莊家的牌面");
                    do {//產生不重複的隨機數
                        random = (int) Math.floor(Math.random() * 52 + 1);
                    } while (data[random] == 0);
                    //發牌
                    dealerHC[ii] = data[random];
                    data[random] = 0;

                    if (dealerHC[ii] % 13 == 1) {//抽到A
                        flag = true;
                    }
                    //莊家印牌
                    for (int c = 0; c < dealerHC.length; c++) {
                        if (ii == 0 && dealerHC[c] != 0) {
                            printback();
                        } else {
                            if (c == 0) {
                                printback();
                            } else if (dealerHC[c] != 0) {
                                a = dealerHC[c] / 13;
                                if (dealerHC[c] % 13 == 0) {
                                    a--;
                                }
                                if (dealerHC[c] % 13 == 0) {
                                    b = 13;
                                } else {
                                    b = dealerHC[c] % 13;
                                }
                                printcard(a, b);
                            }
                        }
                    }
                    System.out.println();

                    //點數計算
                    if (dealerHC[ii] % 13 == 0 || dealerHC[ii] % 13 >= 10) {
                        point = 10;
                    } else if (dealerHC[ii] % 13 == 1) {
                        point = 11;
                    } else {
                        point = dealerHC[ii] % 13;
                    }
                    dealerPt = dealerPt + point;
                    ii++;
                }

                //轉為只看點數不看花色
                for (int j = 0; j < playerHC.length; j++) {
                    if (playerHC[j] == 0) {
                        playerHP[j] = 0;
                    } else if (playerHC[j] % 13 == 0) {
                        playerHP[j] = 13;
                    } else {
                        playerHP[j] = playerHC[j] % 13;
                    }
                }

                //特殊贏法
                if (playerPt == 21 || playerPt2 == 21) {
                    //自然blackjack
                    if ((IntStream.of(playerHP).anyMatch(x -> x == 1) && IntStream.of(playerHP).anyMatch(x -> x == 10)) || (IntStream.of(playerHP).anyMatch(x -> x == 1) && IntStream.of(playerHP).anyMatch(x -> x == 11)) || (IntStream.of(playerHP).anyMatch(x -> x == 1) && IntStream.of(playerHP).anyMatch(x -> x == 12)) || (IntStream.of(playerHP).anyMatch(x -> x == 1) && IntStream.of(playerHP).anyMatch(x -> x == 13))) {
                        win = 1;
                        break;
                    }
                    //同花順
                    if ((IntStream.of(playerHC).anyMatch(x -> x == 6) && IntStream.of(playerHC).anyMatch(x -> x == 7) && IntStream.of(playerHC).anyMatch(x -> x == 8)) || (IntStream.of(playerHC).anyMatch(x -> x == 19) && IntStream.of(playerHC).anyMatch(x -> x == 20) && IntStream.of(playerHC).anyMatch(x -> x == 21) || (IntStream.of(playerHC).anyMatch(x -> x == 32) && IntStream.of(playerHC).anyMatch(x -> x == 33) && IntStream.of(playerHC).anyMatch(x -> x == 34) || (IntStream.of(playerHC).anyMatch(x -> x == 45) && IntStream.of(playerHC).anyMatch(x -> x == 46) && IntStream.of(playerHC).anyMatch(x -> x == 47))))) {

                        win = 2;
                        break;
                    } //順子
                    else if (IntStream.of(playerHP).anyMatch(x -> x == 6) && IntStream.of(playerHP).anyMatch(x -> x == 7) && IntStream.of(playerHP).anyMatch(x -> x == 8)) {

                        win = 3;
                        break;
                    } //三條七
                    else if (!IntStream.of(playerHP).anyMatch(x -> x == 1) && !IntStream.of(playerHP).anyMatch(x -> x == 2) && !IntStream.of(playerHP).anyMatch(x -> x == 3) && !IntStream.of(playerHP).anyMatch(x -> x == 4) && !IntStream.of(playerHP).anyMatch(x -> x == 5) && !IntStream.of(playerHP).anyMatch(x -> x == 6) && !IntStream.of(playerHP).anyMatch(x -> x == 8) && !IntStream.of(playerHP).anyMatch(x -> x == 9) && !IntStream.of(playerHP).anyMatch(x -> x == 10) && !IntStream.of(playerHP).anyMatch(x -> x == 11) && !IntStream.of(playerHP).anyMatch(x -> x == 12) && !IntStream.of(playerHP).anyMatch(x -> x == 13)) {

                        win = 4;
                        break;
                    }
                    break;
                }
                //過五關
                if (playerHC.length == 5 && playerPt <= 21 && !IntStream.of(playerHC).anyMatch(x -> x == 0)) {
                    money = money + 3 * bet;
                    System.out.println("過五關！贏得3倍金幣\n" + "您現在擁有的金幣數" + money);
                    win = 5;
                    break;
                }
                //加注或投降
                if (i == 1) {
                    System.out.println("請問是否加注或投降(加註請輸入「D」，投降請輸入「L」，都不要請輸入「N」)");
                    String DorL = sc.nextLine().toUpperCase();
                    if (DorL.equals("D")) {
                        money = money - bet;
                        bet = bet * 2;
                    } else if (DorL.equals("L")) {
                        money = money + bet / 2;
                        win = 6;
                        leave = true;
                        break;
                    }
                }

                if (i != 0 && playerPt < 21) {
                    System.out.println("請問是否加牌，是，輸入「Y」 ；否，輸入「N」");
                }
                i++;
            } while (i < 2 || playerPt < 21 && sc.nextLine().toUpperCase().equals("Y"));

            while (dealerPt < 17) {//莊家發牌
                do {//產生不重複的隨機數
                    random = (int) Math.floor(Math.random() * 52 + 1);
                } while (data[random] == 0);

                dealerHC[ii] = data[random];
                data[random] = 0;

                if (dealerHC[ii] % 13 == 1) {
                    flag = true;
                }
                //點數計算
                if (dealerHC[ii] % 13 == 0 || dealerHC[ii] % 13 >= 10) {
                    point = 10;
                } else if (dealerHC[ii] % 13 == 1) {
                    point = 11;
                } else {
                    point = dealerHC[ii] % 13;
                }
                dealerPt = dealerPt + point;
                if (flag && dealerPt > 21) {//當有A又爆牌時
                    dealerPt = 0;
                    for (int k = 0; k < dealerHC.length; k++) {//重算點數總和
                        if (dealerHC[k] != 0) {
                            if (dealerHC[k] % 13 == 0 || dealerHC[k] % 13 >= 10) {
                                point = 10;
                            } else if (dealerHC[k] % 13 == 1) {
                                point = 1;
                            } else {
                                point = dealerHC[k] % 13;
                            }
                            dealerPt = dealerPt + point;
                        }
                    }
                }
                ii++;
            }

            //結算頁面
            System.out.println("您的最終牌面");
            for (int p = 0; p < playerHC.length; p++) {
                if (playerHC[p] != 0) {
                    a = playerHC[p] / 13;
                    if (playerHC[p] % 13 == 0) {
                        a--;
                    }
                    if (playerHC[p] % 13 == 0) {
                        b = 13;
                    } else {
                        b = playerHC[p] % 13;
                    }
                    printcard(a, b);
                }
            }
            System.out.println("莊家的最終牌面");
            for (int c = 0; c < dealerHC.length; c++) {
                if (dealerHC[c] != 0) {
                    a = dealerHC[c] / 13;
                    if (dealerHC[c] % 13 == 0) {
                        a--;
                    }
                    if (dealerHC[c] % 13 == 0) {
                        b = 13;
                    } else {
                        b = dealerHC[c] % 13;
                    }

                    printcard(a, b);
                }
            }

            //判斷勝負
            if (playerPt <= 21 && playerPt2 <= 21) {
                playerPt = playerPt2;
            }
            System.out.println("您的最終點數：" + playerPt + "\n" + "莊家的最終點數：" + dealerPt);

            if (win == 0 && playerPt <= 21 && dealerPt <= 21) {
                if (playerPt > dealerPt) {
                    money = money + 2 * bet;
                    System.out.println("點數贏過莊家！贏得2倍金幣\n" + "您現在擁有的金幣數：" + money);
                } else if (playerPt == dealerPt) {
                    money = money + bet;
                    System.out.println("點數與莊家持平！贏回金幣\n" + "您現在擁有的金幣數：" + money);
                } else if (playerPt < dealerPt) {
                    System.out.println("點數低於莊家！失去金幣\n" + "您現在擁有的金幣數：" + money);
                }
            } else if (win == 0 && playerPt > 21 && dealerPt <= 21) {
                System.out.println("爆牌！失去金幣\n" + "您現在擁有的金幣數" + money);
            } else if (win == 0 && playerPt > 21 && dealerPt > 21) {
                money = money + bet;
                System.out.println("玩家與莊家皆爆牌！贏回金幣\n" + "您現在擁有的金幣數" + money);
            } else if (win == 0 && playerPt <= 21 && dealerPt > 21) {
                money = money + 2 * bet;
                System.out.println("莊家爆牌！贏得2倍金幣\n" + "您現在擁有的金幣數：" + money);
            }

            switch (win) {
                case 1:
                    money = money + 2 * bet;
                    System.out.println("Blackjack！贏得2倍金幣\n" + "您現在擁有的金幣數" + money);
                    break;
                case 2:
                    money = money + 15 * bet;
                    System.out.println("同花順！贏得15倍金幣\n" + "您現在擁有的金幣數" + money);
                    break;
                case 3:
                    money = money + 10 * bet;
                    System.out.println("順子！贏得10倍金幣\n" + "您現在擁有的金幣數" + money);
                    break;
                case 4:
                    money = money + 10 * bet;
                    System.out.println("三條七！贏得10倍金幣\n" + "您現在擁有的金幣數" + money);
                    break;
                case 5:
                    money = money + 3 * bet;
                    System.out.println("過五關！贏得3倍金幣\n" + "您現在擁有的金幣數" + money);
                    break;
                default:

                    break;
            }

            //投降時的結算頁面
            if (leave) {
                System.out.println("您選擇了投降，返還一半下注金幣\n您目前的金幣數為：" + money);
            }

            fw.write(String.valueOf(money));
            fw.close();
            System.out.println("請按任意鍵結束");
            sc.nextLine();
        } else if (start.toUpperCase().equals("N")) {
            System.out.println("歡迎下次遊玩");
            fw.write(String.valueOf(money));
            fw.close();
            System.out.println("請按任意鍵結束");
            sc.nextLine();
        } else {
            System.out.println("輸入格式錯誤，請重新開啟遊戲");
            fw.write(String.valueOf(money));
            fw.close();
            System.out.println("請按任意鍵結束");
            sc.nextLine();
        }
    }

    //每日登入獎勵
    public static int odate(int money, String username) throws IOException {
        Date date = new Date();
        long longtime = date.getTime();
        List bankbooklist = new ArrayList();
        String bankbook = "bankbook.txt";
        File fbb = new File(bankbook);
        Scanner sdate = new Scanner(fbb, "UTF-8");
        while (sdate.hasNextLine()) {
            String info = sdate.nextLine();
            bankbooklist.add(info);
        }
        long odate = Long.parseLong(bankbooklist.get(bankbooklist.size() - 1).toString().split("#")[2]);
        if (longtime - odate >= 86400000) {
            money = money + 500;
            System.out.println("恭喜獲得每日登入獎勵 500 金幣，您現在擁有的金幣數" + money);
        }

        FileWriter fw = new FileWriter(fbb, true);
        fw.write("\r\n" + username + "#" + date + "#" + longtime + "#" + "0");
        fw.close();
        return money;
    }

    //印出空白牌面
    private static void printback() {
        int m = 8;
        int n = 8;
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0) {//第一層, 框框最上面
                    System.out.print("\033[34;4m" + "* " + "\033[0m");
                } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                    if (j == 0) {
                        System.out.print("\033[34;4m" + "*" + "\033[0m");
                    } else if (j == n) {
                        System.out.print("\033[34;4m" + " *" + "\033[0m");
                    } else if (i == 1) {
                        System.out.print("JJ");
                    } else if (j == 4 && i == 2) {
                        System.out.print(" " + "JJ");
                    } else if (j == 4 && i == 3) {
                        System.out.print(" " + "JJ");
                    } else if (j == 4 && i == 4) {
                        System.out.print(" " + "JJ");
                    } else if (j == 4 && i == 5) {
                        System.out.print(" " + "JJ");
                    } else if (j == 4 && i == 6) {
                        System.out.print(" " + "JJ");
                    } else if (i == 7 && j == 2) {
                        System.out.print("JJJJJJJ");
                    } else if (i == 6 && j == 2) {
                        System.out.print("JJ");
                    } else if (i == 7 && j == 5) {
                        System.out.print("");
                    } else if (i == 7 && j == 6) {
                        System.out.print("");
                    } else if (j == n - 1) {
                        System.out.print(" ");
                    } else if (i == 5 && j == 3) {
                        System.out.print("  ");
                    } else {
                        System.out.print("  ");
                    }

                } else if (i == m) {//第三層, 框框最下面
                    System.out.print("\033[34;4m" + "* " + "\033[0m");
                }

            }

            System.out.println();
        }
    }

    //印出牌面
    private static void printcard(int x, int y) {
        y = y - 1;
        String[] kinds = {"♠", "♥", "♦", "♣"};
        String[] number = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        int m = 8;
        int n = 8;
        if (y == 0) {//1的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 4 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 4 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        }
        if (y == 1) {//2的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 1 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 1 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 2) {//3的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 1 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 4 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 1 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 4 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 3) {//4的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 1 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 1 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 1 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 1 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 4) {//5的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 1 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 1 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 4 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 1 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 1 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 4 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 5) {//6的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 1 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 1 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 4 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 4 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 1 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 1 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 4 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 4 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 6) {//7的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 1 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 1 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 4 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 4 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 3 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 1 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 1 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 4 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 4 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 3 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 7) {//8的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 1 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 1 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 3 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 3 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 5 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 5 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 1 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 1 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 3 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 3 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 5 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 5 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 8) {//9的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 1 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 1 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 3 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 3 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 5 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 5 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 4 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 1 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 1 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 3 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 3 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 5 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 5 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 4 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 9) {//10的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(number[y]);
                            } else if (i == 1 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 1 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 2 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 3 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 3 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 5 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 5 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 2) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 6) {
                                System.out.print(kinds[x]);
                            } else if (i == 6 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y]);
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print("\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 1 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 1 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 2 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 3 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 3 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 5 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 5 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 2) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 6) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 6 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 10) {//11的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 4 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 2 && j == 6) {
                                System.out.print("⚔");
                            } else if (i == 6 && j == 2) {
                                System.out.print("⚔");
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 2 && j == 6) {
                                System.out.print("\033[31;4m" + "⚔" + "\033[0m");
                            } else if (i == 4 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 6 && j == 2) {
                                System.out.print("\033[31;4m" + "⚔" + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 11) {//12的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 4 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 6 && j == 2) {
                                System.out.print("♛");
                            } else if (i == 2 && j == 6) {
                                System.out.print("♛");
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 2 && j == 6) {
                                System.out.print("\033[31;4m" + "♛" + "\033[0m");
                            } else if (i == 4 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 6 && j == 2) {
                                System.out.print("\033[31;4m" + "♛" + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        } else if (y == 12) {//13的東西
            if (x == 0 || x == 3) {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("* ");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("*");
                            } else if (j == n) {
                                System.out.print(" *");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + number[y]);
                            } else if (i == 4 && j == 4) {
                                System.out.print(kinds[x]);
                            } else if (i == 2 && j == 6) {
                                System.out.print("♚");
                            } else if (i == 6 && j == 2) {
                                System.out.print("♚");
                            } else if (i == 7 && j == 7) {
                                System.out.print(number[y] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("* ");
                        }
                    }
                    System.out.println();
                }
            } else {
                for (int i = 0; i <= m; i++) {
                    for (int j = 0; j <= n; j++) {
                        if (i == 0) {//第一層, 框框最上面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        } else if (i > 0 && i < m) {//第二層, 框框中間層左右兩條線
                            if (j == 0) {
                                System.out.print("\033[31;4m" + "*" + "\033[0m");
                            } else if (j == n) {
                                System.out.print("\033[31;4m" + " *" + "\033[0m");
                            } else if (i == 1 && j == 1) {
                                System.out.print(" " + "\033[31;4m" + number[y] + "\033[0m");
                            } else if (i == 2 && j == 6) {
                                System.out.print("\033[31;4m" + "♚" + "\033[0m");
                            } else if (i == 4 && j == 4) {
                                System.out.print("\033[31;4m" + kinds[x] + "\033[0m");
                            } else if (i == 6 && j == 2) {
                                System.out.print("\033[31;4m" + "♚" + "\033[0m");
                            } else if (i == 7 && j == 7) {
                                System.out.print("\033[31;4m" + number[y] + " " + "\033[0m");
                            } else {
                                System.out.print("  ");
                            }
                        } else if (i == m) {//第三層, 框框最下面
                            System.out.print("\033[31;4m" + "* " + "\033[0m");
                        }
                    }
                    System.out.println();
                }
            }
        }
    }
}
