<html>
<head>
    <title>Stock Ticker</title>
    <script src="${context}/webjars/jquery/2.2.4/jquery.min.js"></script>
    <script src="${context}/webjars/sockjs-client/1.5.1/sockjs.min.js"></script>
    <script src="${context}/webjars/stomp-websocket/2.3.4/stomp.min.js"></script>
    <script>
        var stomp = Stomp.over(new SockJS("/stomp/ws"));

        function displayStockPrice(frame) {
            var prices = JSON.parse(frame.body);
            $('.price').empty();
            for (var i in prices) {
                var price = pricesi;
                $('.price').append(
                    $('<tr>').append(
                        $('<td>').html(price.code),
                        $('<td>').html(price.price.toFixed(2)),
                        $('<td>').html(price.dateFormatted)
                    )
                )
            }
        }

        var connectCollback = function () {
            alert('connected');
            stomp.subscribe('/sn/topic/price', displayStockPrice);
        };

        var errorCallback = function (error) {
            alert(error.headers.message);
        };

        alert('trying to connect' + stomp);
        stomp.connect("guest", "guest", connectCollback, errorCallback);

        $(document).ready(function () {
            $('.addStockButton').click(function (e) {

                alert('adding button');
                e.preventDefault();
                var jsonStr = JSON.stringify({
                    'code': $('.addStock.code').val(),
                    'price': Number($('.addStock.price').val()),
                });
                alert(stomp);
                stomp.send("/sn/addStock", {}, jsonStr);
                return false;
            });
        });
    </script>
</head>
<body>
<H1>Stock Ticker</H1>
<table border="1">
    <thead>
    <tr>
        <td>Code</td>
        <td>Price</td>
        <td>Time</td>
    </tr>
    <tbody id="price"></tbody>
    </thead>
</table>
<p class="addStock">
    Code: <input class="code"/><br>
    Price: <input class="price"/><br>
    <button class="addStockButton">Add stock</button>
</p>
</body>
</html>