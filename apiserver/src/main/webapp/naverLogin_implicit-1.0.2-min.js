
BUTTON_TYPE=1;BANNER_SMALL_TYPE=2;BANNER_BIG_TYPE=3;BUTTON_COLOR_WHITE="white";BUTTON_COLOR_GREEN="green";var naver_id_login=function(client_id,redirect_uri)
{this.button_color=BUTTON_COLOR_GREEN;this.button_type=BUTTON_TYPE;this.button_height=40;this.nil_domain="";this.response_type="token";this.authorize_url="https://nid.naver.com/oauth2.0/authorize";this.state="";this.scope="";this.client_id=client_id;this.redirect_uri=redirect_uri;this.cookie_name="nil_state";this.popup=false;this.oauthParams={};this.profileParams={};this.is_callback=false;this.callback_status="";this.callback_message="";this.setPopup=function(){this.popup=true;}
this.setState=function(state_value){this.state=((typeof(state_value)!='undefined')&&(state_value!=""))?state_value:"";}
this.setDomain=function(domain_value){this.nil_domain=((typeof(domain_value)!='undefined')&&(domain_value!=""))?domain_value:"";}
this.setButton=function(button_color,button_type,button_height){this.button_color=((typeof(button_color)!='undefined')&&(button_color!=""))?button_color:BUTTON_COLOR_GREEN;this.button_type=((typeof(button_type)!='undefined')&&(button_type!=""))?button_type:BUTTON_TYPE;this.button_height=((typeof(button_height)!='undefined')&&(button_height!=""))?button_height:40;}
this.getUniqState=function(){var stat_str='xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g,function(c){var r=Math.random()*16|0,v=c==='x'?r:(r&0x3|0x8);return v.toString(16);});return stat_str;};this.getLocalStorageItemSafely=function(){try{var item=localStorage.getItem(this.cookie_name);if(item==null||item.length==0){return item;}
return item.replace(/&/g,'&amp;').replace(/"/g,'&quot;').replace(/'/g,'&#x27;').replace(/</g,'&lt;').replace(/>/g,'&gt;');}catch(e)
{return null;}}
this.setStateStore=function()
{try{if(this.nil_domain!="")
{document.cookie=this.cookie_name+"=; path=/; domain="+this.nil_domain+"; expires=Thu, 01 Jan 1970 00:00:00 UTC;";}
else
{document.cookie=this.cookie_name+"=; path=/; expires=Thu, 01 Jan 1970 00:00:00 UTC";}
localStorage.setItem('nil_state',this.state);if(this.nil_domain!="")
{var today=new Date();var expire=new Date(today.getTime()+60*5*1000);var curCookie=this.cookie_name+"="+escape(this.state)+"; expires="
+expire.toGMTString()+"; domain="+this.nil_domain+";path=/;";document.cookie=curCookie;}}catch(e)
{var today=new Date();var expire=new Date(today.getTime()+60*5*1000);var curCookie=this.cookie_name+"="+escape(this.state)+"; expires="
+expire.toGMTString()+";path=/;";document.cookie=curCookie;}}
this.getNaverIdLoginLink=function()
{if(!this.is_callback)
{this.setStateStore();}
else
{this.state=this.oauthParams.state;}
if((this.client_id==undefined)||(this.client_id=="등록한 ClientID 값")||(this.client_id.length<5))
{alert("등록한 ClientID 값을 입력해 주세요.");return false;}
if((this.redirect_uri==undefined)||(this.redirect_uri=="등록한 Callback URL 값")||(this.redirect_uri.length<5))
{alert("등록한 Callback URL 값을 입력해 주세요.");return false;}
call_url=this.authorize_url+"?response_type="+this.response_type+"&client_id="+this.client_id+"&redirect_uri="+encodeURIComponent(this.redirect_uri)+"&state="+encodeURIComponent(this.state);if(this.scope!="")
{call_url=call_url+"&scope="+encodeURIComponent(this.scope);}
return call_url;}
this.init_naver_id_login=function()
{var naver_id_login=document.getElementById('naver_id_login');if(naver_id_login==undefined)
{alert("id 가 naver_id_login 인 div tag 가 존재해야 합니다.");return false;}
if(this.button_color=="green")
{color="g";}
else
{color="w";}
naver_id_login.innerHTML="";naver_id_login_contents="";naver_id_login_url=this.getNaverIdLoginLink();if(this.state==undefined||this.state=="")
{this.state=this.getUniqState();}
naver_id_popup_option="";if(this.popup)
{naver_id_popup_option=" onClick=\"window.open(this.href, 'naverloginpop', 'titlebar=1, resizable=1, scrollbars=yes, width=600, height=550'); return false\" ";}
if(this.button_type==BUTTON_TYPE)
{naver_id_login_contents="<a href='"+naver_id_login_url+"' "+naver_id_popup_option+" id='naver_id_login_anchor'><img src='http://static.nid.naver.com/oauth/button_"+color+".PNG' border='0' title='네이버 아이디로 로그인' width='"+this.button_height+"' height='"+this.button_height+"'></a> ";}
else if(this.button_type==BANNER_SMALL_TYPE)
{naver_id_login_contents="<a href='"+naver_id_login_url+"' "+naver_id_popup_option+" id='naver_id_login_anchor'><img src='http://static.nid.naver.com/oauth/small_"+color+"_in.PNG' border='0' title='네이버 아이디로 로그인' width='"+(this.button_height*656/250)+"px' height='"+this.button_height+"'></a> ";}
else
{naver_id_login_contents="<a href='"+naver_id_login_url+"' "+naver_id_popup_option+" id='naver_id_login_anchor'><img src='http://static.nid.naver.com/oauth/big_"+color+".PNG' border='0' title='네이버 아이디로 로그인' width='"+(this.button_height*185/40)+"px' height='"+this.button_height+"px'></a> ";}
naver_id_login.innerHTML=naver_id_login_contents;if(this.is_callback)
{this.init_naver_id_login_callback();}}
this.checkStateStore=function(receive_state)
{if(this.state!=undefined||this.state=="")
{state=this.getLocalStorageItemSafely();}
else
{state=this.state;}
if(state!=null&&state.length>10)
{if(state==receive_state)
{try{localStorage.removeItem(this.cookie_name)}catch(e){}
return true;}
else
{try{localStorage.removeItem(this.cookie_name)}catch(e){}
return false;}}
else
{if(this.state!=undefined||this.state=="")
{state=this.getCookie();}
else
{state=this.state;}
if(state!=null&&state.length>10)
{if(state==receive_state)
{if(this.nil_domain!=""){document.cookie=this.cookie_name+"=; path=/; domain="+this.nil_domain+"; expires=Thu, 01 Jan 1970 00:00:00 UTC;";}else{document.cookie=this.cookie_name+"=; path=/; expires=Thu, 01 Jan 1970 00:00:00 UTC";}
return true;}
else
{if(this.nil_domain!=""){document.cookie=this.cookie_name+"=; path=/; domain="+this.nil_domain+"; expires=Thu, 01 Jan 1970 00:00:00 UTC;";}else{document.cookie=this.cookie_name+"=; path=/; expires=Thu, 01 Jan 1970 00:00:00 UTC";}
return false;}}
return false;}}
this.getCookie=function(){var b="nil_state=";var c=b.length;var d=document.cookie.length;var e=0;while(e<d){var f=e+c;if(document.cookie.substring(e,f)==b){var g=document.cookie.indexOf(";",f);if(g==-1)g=document.cookie.length;return unescape(document.cookie.substring(f,g))}
e=document.cookie.indexOf(" ",e)+1;if(e==0)break}
return null}
this.parseCallBack=function(){var params={};var queryString=(document.location+"").substring(1);var regex=/([^#?&=]+)=([^&]*)/g;var match;while((match=regex.exec(queryString))!==null){params[decodeURIComponent(match[1])]=decodeURIComponent(match[2]);}
this.oauthParams=params;}
this.parseCallBack_check=function(){this.parseCallBack();if(this.oauthParams.access_token!=undefined)
{this.is_callback=true;}
else
{this.is_callback=false;}}
this.init_naver_id_login_callback=function(){this.parseCallBack_check();if(this.is_callback)
{if(this.oauthParams.error==undefined)
{if(this.oauthParams.access_token!=undefined)
{if(this.checkStateStore(this.oauthParams.state))
{this.callback_status="success";this.callback_message="state check success";}
else
{if(this.state==this.oauthParams.state)
{this.callback_status="success";this.callback_message="state check success";}
else
{alert("state 값이 맞이 않습니다.");this.callback_status="warning";this.callback_message="state miss match";}}}}
else
{this.callback_status="fail";this.callback_message="invalid access";}}}
this.parseCallBack_check();this.get_naver_userprofile=function(callback_func1){$.ajax({url:"https://openapi.naver.com/v1/nid/getUserProfile.json?response_type=json",type:"GET",data:{"access_token":this.oauthParams.access_token},dataType:"jsonp",jsonp:"oauth_callback",success:function(result){inner_profileParams.age=result.response.age;inner_profileParams.birthday=result.response.birthday;inner_profileParams.email=result.response.email;inner_profileParams.enc_id=result.response.enc_id;inner_profileParams.gender=result.response.gender;inner_profileParams.id=result.response.id;inner_profileParams.nickname=result.response.nickname;inner_profileParams.profile_image=result.response.profile_image;eval(callback_func1);},error:function(xhr,ajaxOptions,thrownError){alert(xhr.status);alert(thrownError);}});}
this.getProfileData=function(name){return inner_profileParams[name];}
this.getOauthMessage=function()
{return this.callback_message;}
this.getOauthStatus=function()
{return this.callback_status;}
this.getAccessToken=function()
{return this.oauthParams.access_token;}}
var inner_profileParams={};
