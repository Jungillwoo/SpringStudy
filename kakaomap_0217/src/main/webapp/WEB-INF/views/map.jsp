<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Kakao Map API - Marker Info</title>
    <style>
        /* 전체 화면 */
        html, body {
            width: 100%;
            height: 100%;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }
        #map {
            width: 100vw;
            height: 100vh;
        }

        /* 말풍선 스타일 */
        .custom-overlay {
            background: white;
            padding: 5px 10px;
            border-radius: 5px;
            box-shadow: 0px 2px 5px rgba(0,0,0,0.2);
            font-size: 12px;
            font-weight: bold;
            position: relative;
            text-align: center;
            border: 1px solid #ddd;
            white-space: nowrap;
        }

        /* 말풍선 아래 삼각형 (꼬리) */
        .custom-overlay:after {
            content: "";
            position: absolute;
            bottom: -8px;
            left: 50%;
            transform: translateX(-50%);
            border-width: 8px;
            border-style: solid;
            border-color: white transparent transparent transparent;
        }
    </style>
</head>
<body>

<div id="map"></div>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5c189a02814555dc1e1dc1378c43745b"></script>
<script>
  var container = document.getElementById('map');
  var options = {
    center: new kakao.maps.LatLng(37.521162, 126.940287),
    level: 3
  };

  var map = new kakao.maps.Map(container, options);

  // 지도 클릭 이벤트 등록
  kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
    var latlng = mouseEvent.latLng;

    // 새로운 마커 생성
    var marker = new kakao.maps.Marker({
      position: latlng,
      map: map
    });

    // 마커 아래에 표시할 말풍선 형태의 HTML
    var infoContent = '<div class="custom-overlay">' +
        '위도: ' + latlng.getLat().toFixed(6) + '<br>경도: ' + latlng.getLng().toFixed(6) +
        '</div>';

    // 커스텀 오버레이 생성 (마커 아래 위치 조정)
    var customOverlay = new kakao.maps.CustomOverlay({
      position: latlng,
      content: infoContent,
      yAnchor: 1.2 // 마커 아래쪽으로 배치
    });

    // 지도에 오버레이 표시
    customOverlay.setMap(map);
  });
</script>

</body>
</html>