/**
 * Created by hrhnyy on 2017/9/14.
 */
Ext.define('cmsapp.view.login.Login', {
    extend: 'cmsapp.view.login.LockingWindow',
    xtype: 'login',
    requires: [
        'Admin.view.authentication.Dialog',
        'Ext.container.Container',
        'Ext.form.field.Text',
        'Ext.form.field.Checkbox',
        'Ext.button.Button'
    ],

    title: '华润河南医药有限公司',
    //默认页面焦点位置
    defaultFocus: 'authdialog',

    items: [
        {
            xtype: 'authdialog',
            defaultButton: 'loginButton',
            autoComplete: true,
            bodyPadding: '20 20',
            cls: 'auth-dialog-login',
            header: false,
            width: 415,
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            defaults: {
                margin: '5 0'
            },
            items: [
                {
                    xtype: 'label',
                    text: '登录帐号'
                },
                {
                    xtype: 'textfield',
                    cls: 'auth-textbox',
                    name: 'userid',
                    bind: '{userid}',
                    height: 55,
                    hideLabel: true,
                    allowBlank: false,
                    emptyText: '用户帐号',
                    triggers: {
                        glyphed: {
                            cls: 'trigger-glyph-noop auth-email-trigger'
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    cls: 'auth-textbox',
                    height: 55,
                    hideLabel: true,
                    emptyText: '密码',
                    inputType: 'password',
                    name: 'password',
                    bind: '{password}',
                    allowBlank: false,
                    triggers: {
                        glyphed: {
                            cls: 'trigger-glyph-noop auth-password-trigger'
                        }
                    }
                },
                {
                    xtype: 'container',
                    layout: 'hbox',
                    items: [
                        {
                            xtype: 'checkboxfield',
                            flex: 1,
                            cls: 'form-panel-font-color rememberMeCheckbox',
                            height: 30,
                            bind: '{persist}',
                            boxLabel: '保持登录'
                        },
                        {
                            xtype: 'box',
                            html: '<a href="#passwordreset" class="link-forgot-password">忘记密码</a>'
                        }
                    ]
                },
                {
                    xtype: 'button',
                    reference: 'loginButton',
                    scale: 'large',
                    ui: 'soft-green',
                    iconAlign: 'right',
                    iconCls: 'x-fa fa-angle-right',
                    text: 'Login',
                    formBind: true,
                    listeners: {
                        click: 'onLoginButton'
                    }
                }
            ]
        }
    ],

    initComponent: function () {
        this.addCls('user-login-register-container');
        this.callParent(arguments);
    }
});
