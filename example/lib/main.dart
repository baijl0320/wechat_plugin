import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:wechat_plugin/wechat_plugin.dart';
import 'package:groovin_material_icons/groovin_material_icons.dart';

//void main() => runApp(MyApp());
void main() => runApp(new MaterialApp(home: new MyApp()));

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  final _formKey = GlobalKey<FormState>();

  String _phoneNumber, _password;
  bool _isObscure = true;
  Color _eyeColor;

  List _loginMethod = [
    {
      "title": "微信",
      "icon":GroovinMaterialIcons.wechat,
    },
    {
      "title": "QQ",
      "icon":GroovinMaterialIcons.qqchat,
    },
  ];

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await WechatPlugin.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

//  @override
//  Widget build(BuildContext context) {
//    return MaterialApp(
//      home: Scaffold(
//        appBar: AppBar(
//          title: const Text('Plugin example app'),
//        ),
//        body: Center(
//          child: Text('Running on: $_platformVersion\n'),
//        ),
//        floatingActionButton: FloatingActionButton(
//          onPressed: _registerWechat,
//          tooltip: 'registerWechat',
//          child: Icon(Icons.play_arrow),
//        ),
//      ),
//    );
//  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        body: Form(
          key: _formKey,
          child: ListView(
            padding: EdgeInsets.symmetric(horizontal: 22.0),
            children: <Widget>[
              SizedBox(
                height: kToolbarHeight,
              ),
              buildTitle(),
              buildTitleLine(),
              SizedBox(height: 70.0,),
              buildEmailTextField(),
              SizedBox(height: 30.0,),
              buildPasswordTextField(context),
              buildForgetPasswordText(context),
              SizedBox(height: 60.0,),
              buildLoginButton(context),
              SizedBox(height: 30.0,),
              buildOtherLoginText(),
              buildOtherLoginMethod(context),
              buildRegisterText(context),
            ],
          ),
        ),
//        floatingActionButton: FloatingActionButton(
//          onPressed: _registerWechat,
//          tooltip: 'registerWechat',
//          child: Icon(Icons.play_arrow),
//        ),
      );
  }

//  @override
//  Widget build(BuildContext context) {
//    return MaterialApp(
//        home: Scaffold(
//          body: Form(
//            key: _formKey,
//            child: ListView(
//              padding: EdgeInsets.symmetric(horizontal: 22.0),
//              children: <Widget>[
//                SizedBox(
//                  height: kToolbarHeight,
//                ),
//                buildTitle(),
//                buildTitleLine(),
//                SizedBox(height: 70.0,),
//                buildEmailTextField(),
//                SizedBox(height: 30.0,),
//                buildPasswordTextField(context),
//                buildForgetPasswordText(context),
//                SizedBox(height: 60.0,),
//                buildLoginButton(context),
//                SizedBox(height: 30.0,),
//                buildOtherLoginText(),
//                buildOtherLoginMethod(context),
//                buildRegisterText(context),
//              ],
//            ),
//          ),
////        floatingActionButton: FloatingActionButton(
////          onPressed: _registerWechat,
////          tooltip: 'registerWechat',
////          child: Icon(Icons.play_arrow),
////        ),
//        )
//    );
//  }

  Padding buildTitle() {
    return Padding(
      padding: EdgeInsets.all(8.0),
      child: Text(
        "Login",
        style: TextStyle(fontSize: 42.0),
      ),
    );
  }

  Padding buildTitleLine() {
    return Padding(
      padding: EdgeInsets.only(left: 12.0, top: 4.0),
      child: Align(
        alignment: Alignment.bottomLeft,
        child: Container(
          color: Colors.black,
          width: 160.0,
          height: 2.0,
        ),
      ),
    );
  }

  TextFormField buildEmailTextField() {
    return TextFormField(
      decoration: InputDecoration(
        labelText: "Mobile Phone Number",
      ),
      validator: (String value) {
        // TODO 判断字符串是否是纯数字组成
        if (value.length != 11) {
          return "请输入正确的手机号码";
        }
      },
      onSaved: (String value) => _phoneNumber = value,
    );
  }

  TextFormField buildPasswordTextField(BuildContext context) {
    return TextFormField(
      onSaved: (String value) => _password = value,
      obscureText: _isObscure,
      validator: (String value) {
        if (value.isEmpty) {
          return "请输入密码";
        }
      },
      decoration: InputDecoration(
        labelText: "Password",
        suffixIcon: IconButton(
          icon: Icon(
            Icons.remove_red_eye,
            color: _eyeColor,
          ),
          onPressed: () {
            setState(() {
              _isObscure = !_isObscure;
              _eyeColor = _isObscure ? Colors.grey : Theme.of(context).iconTheme.color;
            });
          },
        )
      ),
    );
  }

  Padding buildForgetPasswordText(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(top:8.0),
      child: Align(
        alignment: Alignment.centerRight,
        child: FlatButton(
          child: Text(
            "忘记密码？",
            style: TextStyle(fontSize: 14.0, color: Colors.grey),
          ),
          onPressed: () {
            Navigator.pop(context);
          },
        ),
      ),
    );
  }

  Align buildLoginButton(BuildContext context) {
    return Align(
      child: SizedBox(
        height: 45.0,
        width: 270.0,
        child: RaisedButton(
          child: Text(
            "Login",
            style: Theme.of(context).primaryTextTheme.headline,
          ),
          color: Colors.black,
          onPressed: () {
            if (_formKey.currentState.validate()) {
              _formKey.currentState.save();
              // TODO Login
              print("PhoneNumer is $_phoneNumber, password is $_password");
            }
          },
          shape: StadiumBorder(side: BorderSide()),
        )
      ),
    );
  }

  Align buildOtherLoginText() {
    return Align(
      alignment: Alignment.center,
      child: Text(
        "其他账号登录",
        style: TextStyle(color: Colors.grey, fontSize: 14.0),
      ),
    );
  }

  ButtonBar buildOtherLoginMethod(BuildContext context) {
    return ButtonBar(
      alignment: MainAxisAlignment.center,
      children: _loginMethod.map((item) => Builder(
        builder: (context) {
          return IconButton(
            icon: IconButton(
              icon: Icon(item['icon'],
              color: Theme.of(context).iconTheme.color),
              onPressed: () {
                Scaffold.of(context).showSnackBar(new SnackBar(
                    content: new Text("${item['title']}登录"),
                    action: new SnackBarAction(
                        label: "取消",
                        onPressed: (){}
                        ),
                ));
              },
            ),
          );
        },
      )).toList(),
    );
  }

  Align buildRegisterText(BuildContext context) {
    return Align(
      alignment: Alignment.center,
      child: Padding(
        padding: EdgeInsets.only(top: 10.0),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text("没有账号？"),
            GestureDetector(
              child: Text(
                "点击注册",
                style: TextStyle(color: Colors.green),
              ),
              onTap: () {
                print("去注册 ==== ");
//                Navigator.pop(context);
              },
            ),
          ],
        ),
      ),
    );
  }

  void _registerWechat() async {
//    bool aa = await WechatPlugin.registerWechat(appid: 'wx913f02182754ad93');
//    print("_registerWechat $aa");
//    int res = await WechatPlugin.loginWechat();
//    print("_registerWechat $res");

    String str1 = "124455";
    String str2 = "addfasf";
    int number1 = int.parse(str1);
    print("_registerWechat number1 is $number1");
    int number2 = int.parse(str2);
    print("_registerWechat number2 is $number2");
    print("_registerWechat number2 is $number2");
  }
}
