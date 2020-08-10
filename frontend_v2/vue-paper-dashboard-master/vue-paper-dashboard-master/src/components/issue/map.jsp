<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>주소로 장소 표시하기</title>
<style>
.map_wrap {
	position: relative;
	width: 100%;
	height: 350px;
}

.title {
	font-weight: bold;
	display: block;
}

.hAddr {
	position: absolute;
	left: 10px;
	top: 10px;
	border-radius: 2px;
	background: #fff;
	background: rgba(255, 255, 255, 0.8);
	z-index: 1;
	padding: 5px;
}

#centerAddr {
	display: block;
	margin-top: 2px;
	font-weight: normal;
}

.bAddr {
	padding: 5px;
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
}

.placeinfo_wrap {
	position: absolute;
	bottom: 28px;
	left: -150px;
	width: 300px;
}

.placeinfo {
	position: relative;
	width: 100%;
	border-radius: 6px;
	border: 1px solid #ccc;
	border-bottom: 2px solid #ddd;
	padding-bottom: 10px;
	background: #fff;
}

.placeinfo:nth-of-type(n) {
	border: 0;
	box-shadow: 0px 1px 2px #888;
}

.placeinfo_wrap .after {
	content: '';
	position: relative;
	margin-left: -12px;
	left: 50%;
	width: 22px;
	height: 12px;
	background:
		url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')
}

.placeinfo a, .placeinfo a:hover, .placeinfo a:active {
	color: #fff;
	text-decoration: none;
}

.placeinfo a, .placeinfo span {
	display: block;
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
}

.placeinfo span {
	margin: 5px 5px 0 5px;
	cursor: default;
	font-size: 13px;
}

.placeinfo .title {
	font-weight: bold;
	font-size: 14px;
	border-radius: 6px 6px 0 0;
	margin: -1px -1px 0 -1px;
	padding: 10px;
	color: #fff;
	background: #d95050;
	background: #d95050 no-repeat right 14px center;
}

.placeinfo .tel {
	color: #0f7833;
}

.placeinfo .jibun {
	color: #999;
	font-size: 11px;
	margin-top: 0;
}
</style>

</head>
<body>
	<p style="margin-top: -12px">
		<em class="link"> <a href="javascript:void(0);"
			onclick="window.open('http://fiy.daum.net/fiy/map/CsGeneral.daum', '_blank', 'width=981, height=650')">
				혹시 주소 결과가 잘못 나오는 경우에는 여기에 제보해주세요. </a>
		</em>
	</p>

	<div>
		<input type="text" name="address" size="12" id="address">
		<button onclick="javascript:testing()" type="submit">위치 입력</button>
		<button onclick="javascript:address()" type="submit">주소 검색
			api</button>
		<br>
	</div>

	<!-- <div id="map" style="width: 700px; height: 500px;"></div> -->
	<div class="map_wrap">
		<div id="map"
			style="width: 700px; height: 500px; position: relative; overflow: hidden;"></div>
		<div class="hAddr">
			<span class="title">지도중심기준 행정동 주소정보</span> <span id="centerAddr"></span>
		</div>
	</div>

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d0d50b55f6a9816774963048e01b9004&libraries=services"></script>
	<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
	<script>
		function address() {
			new daum.Postcode({
				oncomplete : function(data) {
					alert(data.address);
					// data.address로 회원가입시 주소를 입력하면 됨.
				}
			}).open();
		}

		var geocoder = new kakao.maps.services.Geocoder();
		function testing() {
			var input = document.getElementById("address").value;

			geocoder
					.addressSearch(
							input,
							function(result, status) {
								// 정상적으로 검색이 완료됐으면 
								if (status === kakao.maps.services.Status.OK) {
									markerCenter.setMap(null);
									var coords = new kakao.maps.LatLng(
											result[0].y, result[0].x);

									addMarker(coords);

									var totalX = 0;
									var totalY = 0;
									var len = markers.length;
									console.log("len is " + len);
									for (var i = 0; i < len; i++) {
										totalX += markers[i].getPosition().Ha;
										totalY += markers[i].getPosition().Ga;
									}
									totalX /= len;
									totalY /= len;

									var coords = new kakao.maps.LatLng(totalX,
											totalY);

									markerCenter = new kakao.maps.Marker({
										map : map,
										position : coords,
										image : markerImage
									});
									markerCenter.setMap(map);
									map.setCenter(coords);

									// 마커를 클릭하면 장소명을 표출할 인포윈도우 입니다
									var category = new kakao.maps.InfoWindow({
										zIndex : 1,
										removable : true
									});

									// 장소 검색 객체를 생성합니다
									var ps = new kakao.maps.services.Places(map);

									ps.categorySearch('CE7', placesSearchCB, {
										useMapBounds : true
									});

									// 키워드 검색 완료 시 호출되는 콜백함수 입니다
									function placesSearchCB(data, status,
											pagination) {
										if (status === kakao.maps.services.Status.OK) {
											tmpData = [];
											tmpData.push(data[i]);
											for (var i = 0; i < data.length; i++) {
												displayMarker(data[i]);
											}
										} else {
											alert("주위 검색 실패!")
										}
									}

									// 지도에 마커를 표시하는 함수입니다
									function displayMarker(place) {
										// 마커를 생성하고 지도에 표시합니다

										var imageSrc = 'https://cdn3.iconfinder.com/data/icons/pin-4/100/pin_10-512.png', // 마커이미지의 주소입니다    
										imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
										imageOption = {
											offset : new kakao.maps.Point(27,
													69)
										}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

										// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
										var markerImage = new kakao.maps.MarkerImage(
												imageSrc, imageSize,
												imageOption);

										

										var marker2 = new kakao.maps.Marker({
											map : map,
											position : new kakao.maps.LatLng(
													place.y, place.x),
											image : markerImage
										});

										// 마커에 클릭이벤트를 등록합니다
										kakao.maps.event
												.addListener(
														marker2,
														'click',
														function() {
															// 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다

															var content = '<div class="placeinfo">'
																	+ '   <a class="title" href="' + place.place_url + '" target="_blank" title="' + place.place_name + '">'
																	+ place.place_name
																	+ '</a>';

															if (place.road_address_name) {
																content += '    <span title="' + place.road_address_name + '">'
																		+ place.road_address_name
																		+ '</span>'
																		+ '  <span class="jibun" title="' + place.address_name + '">(지번 : '
																		+ place.address_name
																		+ ')</span>';
															} else {
																content += '    <span title="' + place.address_name + '">'
																		+ place.address_name
																		+ '</span>';
															}

															content += '    <span class="tel">'
																	+ place.phone
																	+ '</span>'
																	+ '</div>'
																	+ '<div class="after"></div>';
															/* category
																	.setContent('<div style="padding:5px;font-size:12px;">'
																			+ place.place_name
																			+ '</div>'); */
															category
																	.setContent(content);
															category.open(map,
																	marker2);
														});
									}

									var iwContent = '<div style="padding:5px;">Center</div>';

									// 인포윈도우를 생성합니다
									var infowindow = new kakao.maps.InfoWindow(
											{
												content : iwContent
											});

									// 마커에 마우스오버 이벤트를 등록합니다
									kakao.maps.event.addListener(markerCenter,
											'mouseover', function() {
												// 마커에 마우스오버 이벤트가 발생하면 인포윈도우를 마커위에 표시합니다
												infowindow.open(map,
														markerCenter);
											});

									// 마커에 마우스아웃 이벤트를 등록합니다
									kakao.maps.event.addListener(markerCenter,
											'mouseout', function() {
												// 마커에 마우스아웃 이벤트가 발생하면 인포윈도우를 제거합니다
												infowindow.close();
											});

									// markerCenter 에 대한 주소 상세
									searchDetailAddrFromCoords(
											coords,
											function(result, status) {
												if (status === kakao.maps.services.Status.OK) {
													var detailAddr = !!result[0].road_address ? '<div>도로명주소 : '
															+ result[0].road_address.address_name
															+ '</div>'
															: '';
													detailAddr += '<div>지번 주소 : '
															+ result[0].address.address_name
															+ '</div>';

													var content = '<div class="bAddr">'
															+ '<span class="title">법정동 주소정보</span>'
															+ detailAddr
															+ '</div>';

													// 인포윈도우에 클릭한 위치에 대한 법정동 상세 주소정보를 표시합니다
													infowindow
															.setContent(content);
													/* infowindow
															.open(map, marker); */
												}
											});

								} else {
									alert("검색 실패!");
								}
							});

		}

		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			level : 3
		// 지도의 확대 레벨
		};

		var markerCenter = new kakao.maps.LatLng(33.450701, 126.570667);

		// 지도를 생성합니다    
		var map = new kakao.maps.Map(mapContainer, mapOption);

		var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 마커이미지의 주소입니다    
		imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
		imageOption = {
			offset : new kakao.maps.Point(27, 69)
		}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

		// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize,
				imageOption);

		// 마커를 생성합니다
		markerCenter = new kakao.maps.Marker({
			position : new kakao.maps.LatLng(33.450701, 126.570667),
			image : markerImage
		// 마커이미지 설정 
		});

		var markers = [];

		var tmpData = [];

		// 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
		searchAddrFromCoords(map.getCenter(), displayCenterInfo);

		function addMarker(position) {

			// 마커를 생성합니다

			var marker = new kakao.maps.Marker({
				position : position
			});

			// 마커가 지도 위에 표시되도록 설정합니다
			marker.setMap(map);

			// 생성된 마커를 배열에 추가합니다
			markers.push(marker);
		}

		// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
		function setMarkers(map) {
			for (var i = 0; i < markers.length; i++) {
				markers[i].setMap(map);
			}
		}

		// 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
		kakao.maps.event.addListener(map, 'idle', function() {
			searchAddrFromCoords(map.getCenter(), displayCenterInfo);
		});

		function searchAddrFromCoords(coords, callback) {
			// 좌표로 행정동 주소 정보를 요청합니다
			geocoder.coord2RegionCode(coords.getLng(), coords.getLat(),
					callback);
		}

		function searchDetailAddrFromCoords(coords, callback) {
			// 좌표로 법정동 상세 주소 정보를 요청합니다
			geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
		}

		// 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
		function displayCenterInfo(result, status) {
			if (status === kakao.maps.services.Status.OK) {
				var infoDiv = document.getElementById('centerAddr');

				for (var i = 0; i < result.length; i++) {
					// 행정동의 region_type 값은 'H' 이므로
					if (result[i].region_type === 'H') {
						infoDiv.innerHTML = result[i].address_name;
						break;
					}
				}
			}
		}
	</script>
</body>
</html>