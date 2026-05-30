# RMI Quiz System

ဒီ Project ဟာ Java RMI (Remote Method Invocation) ကို အသုံးပြုပြီး တည်ဆောက်ထားတဲ့ Quiz Application တစ်ခုဖြစ်ပါတယ်။

## အသုံးပြုရန် လိုအပ်ချက်များ (Prerequisites)
- Java Development Kit (JDK) 8 သို့မဟုတ် အထက်ရှိရန်။
- VS Code သို့မဟုတ် Java ကို Run နိုင်သည့် IDE တစ်ခုခု။

## Run လုပ်ရန် အဆင့်ဆင့်
1. Compile လုပ်ပါ:
   Terminal တွင် အောက်ပါ Command ကို ရိုက်ပါ:
   `bash
   javac *.java

2. Run Server
   
   java QuizeServer

3. Run Client
   
   java QuizClientGUI

Note : Use with your Local IP Address


## တခြားစက်နှင့် ချိတ်ဆက်၍ အသုံးပြုပုံ (Network Setup)
သင်၏ RMI Quiz System ကို စက်နှစ်လုံး (Client နှင့် Server) တွဲသုံးလိုပါက အောက်ပါအတိုင်း ပြင်ဆင်ပါ -

၁။ Server IP ကို ရှာပါ: Server စက်တွင် CMD သို့မဟုတ် Terminal ဖွင့်ပြီး ipconfig (Windows) သို့မဟုတ် ifconfig (Linux/Mac) ရိုက်၍ IPv4 Address ကို ရှာပါ။ (ဥပမာ: 192.168.1.X)

၂။ Code ပြင်ဆင်ပါ:
   - QuizServer.java နှင့် QuizClientGUI.java ထဲရှိ IP address နေရာတွင် အထက်ပါ Server IP အမှန်ကို အစားထိုးပေးပါ။

၃။ Firewall ခွင့်ပြုပါ:
   - Server စက်၏ Windows Firewall တွင် Port 1099 ကို Inbound Rule အနေဖြင့် အသုံးပြုခွင့်ပေးထားရန် လိုအပ်ပါသည်။

၄။ Network: Client နှင့် Server စက်နှစ်လုံးစလုံးသည် တူညီသော Wi-Fi (Router) တစ်ခုတည်းတွင် ချိတ်ဆက်ထားရပါမည်။

