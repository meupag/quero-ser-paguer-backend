exports.usFormat = function (number) {
    // define the appropriate separator based on the number input
    var decimalSeparator = ",";
    var thousandSeparator = ".";

    // make sure we have a string
    var result = String(number);

    // split the number in the integer and decimals, if any
    var parts = result.split(decimalSeparator);

    // if we don't have decimals, add .00
    if (!parts[1]) {
      parts[1] = "00";
    } else if (Number(parts[1]) < 10 && Number(parts[1]) > 0 && parts[1].length === 1) {
      parts[1] += "0";
    };
  
    // reverse the string (1719 becomes 9171)
    result = parts[0].split("").reverse().join("");

    // add (thousand, for US) (decimal, for BR) ("", for no) separator each 3 characters, except at the end of the string
    result = result.replace(/(\d{3}(?!$))/g, "$1" + "");

    // reverse back the integer and replace the original integer
    parts[0] = result.split("").reverse().join("");

    // recombine integer with decimals separator (or with thousand separator, depending on the expected number output)
    var final = parts.join(thousandSeparator);

    // return a number
    return Number(final);
};

exports.brFormat = function (number) {
    // define the appropriate separator based on the number input
    var decimalSeparator = ".";
    var thousandSeparator = ",";

    // make sure we have a string
    var result = String(number);

    // split the number in the integer and decimals, if any
    var parts = result.split(decimalSeparator);

    // if we don't have decimals, add .00
    if (!parts[1]) {
      parts[1] = "00";
    } else if (Number(parts[1]) < 10 && Number(parts[1]) > 0 && parts[1].length === 1) {
      parts[1] += "0";
    };

    if (parts[1].length > 2) {
        parts[1] = parts[1].slice(0, 2);
    }
  
    // reverse the string (1719 becomes 9171)
    result = parts[0].split("").reverse().join("");

    // add (thousand, for US) (decimal, for BR) ("", for no) separator each 3 characters, except at the end of the string
    result = result.replace(/(\d{3}(?!$))/g, "$1" + decimalSeparator);

    // reverse back the integer and replace the original integer
    parts[0] = result.split("").reverse().join("");

    // recombine integer with decimals separator (or with thousand separator, depending on the expected number output)
    return parts.join(thousandSeparator);
};