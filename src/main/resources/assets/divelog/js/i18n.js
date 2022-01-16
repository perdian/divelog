function localeNumberParse(stringNumber) {
    let locale = $("html").attr("lang");
    let thousandSeparator = Intl.NumberFormat(locale).format(11111).replace(/\p{Number}/gu, '');
    let decimalSeparator = Intl.NumberFormat(locale).format(1.1).replace(/\p{Number}/gu, '');
    return parseFloat(stringNumber
        .replace(new RegExp('\\' + thousandSeparator, 'g'), '')
        .replace(new RegExp('\\' + decimalSeparator), '.')
    );
}

function localeNumberFormat(number) {
    let locale = $("html").attr(("lang")).replace(/_/g, "-");
    return number.toLocaleString(locale, { maximumFractionDigits: 6 } )
}
