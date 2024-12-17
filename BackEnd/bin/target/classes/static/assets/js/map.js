var positions = []; // marker 배열.

function initMakeMarkers(){
	let list = document.getElementById("resultTable");
	let rowList = list.rows;
	for(let i = 1; i < rowList.length; i++){
		var mapInfo = rowList[i].cells[0].innerText;
		var latitude = rowList[i].cells[4].innerText;
		var longitude = rowList[i].cells[5].innerText;
		let markerInfo = {
				title: mapInfo,
				latlng: new kakao.maps.LatLng(latitude, longitude),
		};
		positions.push(markerInfo);
	}
	
	displayMarker();
// 카카오지도
	var mapContainer = document.getElementById("map"), // 지도를 표시할 div
	mapOption = {
		center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
		level: 5, // 지도의 확대 레벨
	};
	
// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
	map.setCenter(positions[0].latlng);
}

function displayMarker() {
// 마커 이미지의 이미지 주소입니다
	var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
	for (var i = 0; i < positions.length; i++) {
		console.log("???");
  // 마커 이미지의 이미지 크기 입니다
		var imageSize = new kakao.maps.Size(24, 35);
  // 마커 이미지를 생성합니다
		var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

  // 마커를 생성합니다
		var marker = new kakao.maps.Marker({
			map: map, // 마커를 표시할 지도
			position: positions[i].latlng, // 마커를 표시할 위치
			title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
			image: markerImage // 마커 이미지
		});
		marker.setMap(map);
		var iwContent = '<div style="display: inline-block; width: 200px; white-space: nowrap; padding:5px;">' + positions[i].title+ '</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
	    iwRemoveable = true;
		//인포윈도우를 생성합니다
		var infowindow = new kakao.maps.InfoWindow({
			position : positions[i].latlng, 
			content : iwContent 
		});
	kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(map, marker, infowindow));
	kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
	}
}
// 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
function makeOverListener(map, marker, infowindow) {
    return function() {
        infowindow.open(map, marker);
    };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다 
function makeOutListener(infowindow) {
    return function() {
        infowindow.close();
    };
}
function moveCenter(lat, lng) {
	map.setCenter(new kakao.maps.LatLng(lat, lng));
}