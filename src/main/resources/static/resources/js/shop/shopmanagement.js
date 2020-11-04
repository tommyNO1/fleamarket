$(function () {
    var shopId = getQueryString('shopId');
    var shopInfoUrl = '/fleamarket/shopadmin/getshopmanagementinfo?shopId=' + shopId;
    $.getJSON(shopInfoUrl, function (data) {
        if (data.redirect) {
            window.location.herf = data.url;
        } else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId;
            }
        }
        $('#shopInfo').attr('href','/fleamarket/shopadmin/shopoperation?shopId='+shopId);
    });
});