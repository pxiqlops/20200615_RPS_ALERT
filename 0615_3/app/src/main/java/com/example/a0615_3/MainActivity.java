package com.example.a0615_3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int win = 0;
    int winflg = 0;
    int draw = 0;
    int lose = 0;

    Notification notification = null;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 以下通知発行
        // レイアウトファイルをコンテントビューとしてセット
        setContentView(R.layout.activity_main);
        // システムから通知マネージャー取得
        final NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        // アプリ名をチャンネルIDとして利用
        String chID = getString(R.string.app_name);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {     //APIが「26」以上の場合

            //通知チャンネルIDを生成してインスタンス化
            NotificationChannel notificationChannel = new NotificationChannel(chID, chID, NotificationManager.IMPORTANCE_DEFAULT);
            //通知の説明のセット
            notificationChannel.setDescription(chID);
            //通知チャンネルの作成
            notificationManager.createNotificationChannel(notificationChannel);
            //通知の生成と設定とビルド
            notification = new Notification.Builder(this, chID)
                    .setContentTitle("勝利のお知らせ")  //通知タイトル
                    .setContentText("じゃんけん勝利")        //通知内容
                    .setSmallIcon(R.drawable.icon)                  //通知用アイコン
                    .build();                                       //通知のビルド
        } else {
            //APIが「25」以下の場合（26以上のため今回の環境では使用されない）
            //通知の生成と設定とビルド
            notification = new Notification.Builder(this)
                    .setContentTitle("勝利のお知らせ")  //通知タイトル
                    .setContentText("じゃんけん勝利")        //通知内容
                    .setSmallIcon(R.drawable.icon)
                    .build();
        }

        final TextView text = (TextView)findViewById(R.id.text);
        final TextView cpu2 = (TextView)findViewById(R.id.cpu2);
        final TextView you2 = (TextView)findViewById(R.id.you2);
        final TextView title = (TextView)findViewById(R.id.title);

        Button rock = (Button)findViewById(R.id.rock);
        Button scissors = (Button)findViewById(R.id.scissors);
        Button paper = (Button)findViewById(R.id.paper);

        final TextView WinC = (TextView)findViewById(R.id.WinC);
        final TextView DrawC = (TextView)findViewById(R.id.DrawC);
        final TextView LoseC = (TextView)findViewById(R.id.LoseC);


        // グーボタン押下時の処理
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int opponentHand = decideOpponentHand();
                String opponentHandText = changeTextOpponentHand(opponentHand);
                decideGame(1, opponentHand, title);
                cpu2.setText(opponentHandText);
                you2.setText("グー");

                //　勝利、引き分け、敗北数カウント
                WinC.setText(String.valueOf(win));
                DrawC.setText(String.valueOf(draw));
                LoseC.setText(String.valueOf(lose));

                // じゃんけん勝利時に通知を行う
                if (winflg == 1){
                    notificationManager.notify(1, notification);
                    winflg = 0;
                }
            }
        });

        // チョキボタン押下時の処理
        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int opponentHand = decideOpponentHand();
                String opponentHandText = changeTextOpponentHand(opponentHand);
                decideGame(2, opponentHand, title);
                cpu2.setText(opponentHandText);
                you2.setText("チョキ");

                //　勝利、引き分け、敗北数カウント
                WinC.setText(String.valueOf(win));
                DrawC.setText(String.valueOf(draw));
                LoseC.setText(String.valueOf(lose));

                // じゃんけん勝利時に通知を行う
                if (winflg == 1){
                    notificationManager.notify(1, notification);
                    winflg = 0;
                }
            }
        });

        // パーボタン押下時の処理
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int opponentHand = decideOpponentHand();
                String opponentHandText = changeTextOpponentHand(opponentHand);
                decideGame(3, opponentHand, title);
                cpu2.setText(opponentHandText);
                you2.setText("パー");

                //　勝利、引き分け、敗北数カウント
                WinC.setText(String.valueOf(win));
                DrawC.setText(String.valueOf(draw));
                LoseC.setText(String.valueOf(lose));

                // じゃんけん勝利時に通知を行う
                if (winflg == 1){
                    notificationManager.notify(1, notification);
                    winflg = 0;
                }
            }
        });
    }

    // 相手の手のテキスト表示用
    String changeTextOpponentHand(int hand) {
        String handText = "";
        if (hand == 1) {
            handText = "グー";
        } else if (hand == 2) {
            handText = "チョキ";
        } else if (hand == 3) {
            handText = "パー";
        }
        return handText;
    }

    // Randamを利用して相手のじゃんけんの手を出力
    int decideOpponentHand() {
        Random rnd = new Random();
        int hand = rnd.nextInt(3)+1;
        return hand;
    }

    // じゃんけんの手の計算用
    void decideGame(int playerHand, int opponentHand, TextView decidetext) {
        String decision;

        if(playerHand == opponentHand) {
            decision = "あいこ";
            draw = draw + 1;
        } else if((playerHand == 3 && opponentHand == 1) || (playerHand+1 == opponentHand)) {
            decision = "あなたの勝ち";
            win = win + 1;
            winflg = 1;
        } else {
            decision = "あなたの負け";
            lose = lose + 1;
        }
        decidetext.setText(decision);
    }
}