//自定义验证邮箱
jQuery.validator.addMethod("email", function(value, element) {
    var tel = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    return this.optional(element) || (tel.test(value));
}, "您的邮箱格式不正确！");

//自定义手机号验证
jQuery.validator.addMethod("isphoneNum", function(value, element) {
    var length = value.length;
    var mobile = /^1[3|5|8]{1}[0-9]{9}$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "请正确填写您的手机号码");

//自定义URL验证
jQuery.validator.addMethod("isURL", function(value, element) {
    var url = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
    return this.optional(element) || (url.test(value));
}, "您填写的URL格式不正确！");

//自定义下拉框选择验证
jQuery.validator.addMethod("isSelect", function(value, element) {
    var select = /^[0-9]*$/;
    return this.optional(element) || (select.test(value));
}, "您需要选择正确的选项！");

