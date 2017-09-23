/**
 * Created by hrhnyy on 2017/9/14.
 */
Ext.define('cmsapp.view.login.LockingWindow', {
    extend: 'Ext.window.Window',
    xtype: 'lockingwindow',
    requires: [
        'cmsapp.view.login.LoginController',
        'Ext.layout.container.VBox'
    ],

    cls: 'auth-locked-window',
    closable: false,
    resizable: false,
    autoShow: true,
    titleAlign: 'center',
    maximized: true,
    modal: true,

    layout: {
        type: 'vbox',
        align: 'center',
        pack: 'center'
    },

    controller: 'authentication'
});