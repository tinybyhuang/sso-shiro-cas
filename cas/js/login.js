$(document).ready(function(){
    //必须字母开头，只允许出现大小写字母、数字、下划线（_）等，不少于5个字符，不多于16个字符
    jQuery.validator.addMethod("account",  function (value, element)  {
         var  account  =  /^[a-zA-Z][a-zA-Z1-9_]*$/ ;
         return   this .optional(element)  ||  (account.test(value));
    } ,  "帐号必须字母开头，只允许出现大小写字母、数字、下划线（_）" );

    jQuery.validator.addMethod("password",  function (value, element)  {
        var  password  =  /[@~`!#$%^&*()_+=|\\?/:;"'{}\[\]]/ ;
        return   this .optional(element)  ||  (password.test(value));
    } ,  "密码必须包含特殊字符" );
    //* boxes animation
    form_wrapper = $('.login_box');
    function boxHeight() {
        form_wrapper.animate({ marginTop : ( - ( form_wrapper.height() / 2) - 24) },400);
    };
    form_wrapper.css({ marginTop : ( - ( form_wrapper.height() / 2) - 24) });
    $('.linkform a,.link_reg a').on('click',function(e){
        var target	= $(this).attr('href'),
            target_height = $(target).actual('height');
        $(form_wrapper).css({
            'height'		: form_wrapper.height()
        });
        $(form_wrapper.find('form:visible')).fadeOut(400,function(){
            form_wrapper.stop().animate({
                height	 : target_height,
                marginTop: ( - (target_height/2) - 24)
            },500,function(){
                $(target).fadeIn(400);
                $('.links_btm .linkform').toggle();
                $(form_wrapper).css({
                    'height'		: ''
                });
            });
        });
        e.preventDefault();
    });

    //* validation
    $('#login_form').validate({
        onkeyup: false,
        errorClass: 'error',
        validClass: 'valid',
        rules: {
            username: { required: true, minlength: 5, maxlength: 16,account:true},
            password: { required: true, minlength: 8,maxlength: 32, password:true}
        },
        messages: {
            username: {
                required: '请输入您的帐号',
                minlength: "帐号不能小于{0}个字符",
                maxlength:"帐号不能大于{0}个字符"
            },
            password: {
                required: '请输入您的密码',
                minlength: "密码不能小于{0}个字符",
                maxlength:"密码不能大于{0}个字符"
            }
           // 长度不少于8个字符，需包含特殊字符
        },
        highlight: function(element) {
            $(element).closest('div').addClass("f_error");
            setTimeout(function() {
                boxHeight()
            }, 200)
        },
        unhighlight: function(element) {
            $(element).closest('div').removeClass("f_error");
            setTimeout(function() {
                boxHeight()
            }, 200)
        },
        errorPlacement: function(error, element) {
            $(element).closest('div').append(error);
        }
    });
});