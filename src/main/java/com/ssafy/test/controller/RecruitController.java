package com.ssafy.test.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.test.model.dto.Addr;
import com.ssafy.test.model.dto.AddrAndTag;
import com.ssafy.test.model.dto.Inter;
import com.ssafy.test.model.dto.Interest;
import com.ssafy.test.model.dto.Pinterest;
import com.ssafy.test.model.dto.Recruit;
import com.ssafy.test.model.dto.RecruitPjt;
import com.ssafy.test.model.dto.RecruitPjtPinterest;
import com.ssafy.test.model.dto.SearchParameter;
import com.ssafy.test.model.dto.TagList;
import com.ssafy.test.model.dto.Two;
import com.ssafy.test.model.dto.Usertag;
import com.ssafy.test.model.service.InterestService;
import com.ssafy.test.model.service.PinterestService;
import com.ssafy.test.model.service.RecruitService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/api/recruit")
public class RecruitController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private RecruitService rService;

	@Autowired
	private InterestService iService;

	@Autowired
	private PinterestService piService;

	@ApiOperation(value = "모든 구인구직 게시판의 정보를 반환한다.", response = List.class)
	@GetMapping("/all/{paging}&cnt={cnt}")
	public ResponseEntity<List<RecruitPjt>> retrieveBoardPaging(@PathVariable int paging, @PathVariable int cnt) throws Exception {
		   Two<Integer, Integer> two = new Two<Integer,Integer>(paging* cnt, cnt);

		List<RecruitPjt> v = rService.selectAllLater(two);

		for (int i = 0; i < v.size(); i++) {
			int result = new Date().compareTo(v.get(i).getEndDate());

			if (result >= 0) { // 앞에 있는게 뒤에있는거보다 더 느리다는 뜻

				v.get(i).setRstate("기한만료");
			} else if (result == -1) { // 앞에 있는게 뒤에있는거보다 더 빠르다는 뜻
				if (v.get(i).getPjtMemberCnt() <= v.get(i).getCnt()) {
					v.get(i).setRstate("모집완료");
				} else
					v.get(i).setRstate("모집중");

			}
		}

		return new ResponseEntity<List<RecruitPjt>>(v, HttpStatus.OK);
	}
	

	@ApiOperation(value = "모든 구인구직 게시판의 정보를 반환한다.", response = List.class)
	@GetMapping
	public ResponseEntity<List<RecruitPjt>> retrieveBoard() throws Exception {

		List<RecruitPjt> v = rService.selectAll();

		for (int i = 0; i < v.size(); i++) {
			int result = new Date().compareTo(v.get(i).getEndDate());

			if (result >= 0) { // 앞에 있는게 뒤에있는거보다 더 느리다는 뜻

				v.get(i).setRstate("기한만료");
			} else if (result == -1) { // 앞에 있는게 뒤에있는거보다 더 빠르다는 뜻
				if (v.get(i).getPjtMemberCnt() <= v.get(i).getCnt()) {
					v.get(i).setRstate("모집완료");
				} else
					v.get(i).setRstate("모집중");

			}
		}

		return new ResponseEntity<List<RecruitPjt>>(v, HttpStatus.OK);
	}
	
	@ApiOperation(value = "모든 구인구직 게시판의 정보를 pjtName과 pinterest도 함께 반환한다.", response = List.class)
	@GetMapping("/selectAllRecruitPjtPinterest")
	public ResponseEntity<List<RecruitPjtPinterest>> selectAllRecruitPjtPinterest() throws Exception {
		List<RecruitPjtPinterest> v = rService.selectAllRecruitPjtPinterest();

		for (int i = 0; i < v.size(); i++) {
			int result = new Date().compareTo(v.get(i).getEndDate());

			if (result >= 0) { // 앞에 있는게 뒤에있는거보다 더 느리다는 뜻

				v.get(i).setRstate("기한만료");
			} else if (result == -1) { // 앞에 있는게 뒤에있는거보다 더 빠르다는 뜻
				if (v.get(i).getPjtMemberCnt() <= v.get(i).getCnt()) {
					v.get(i).setRstate("모집완료");
				} else
					v.get(i).setRstate("모집중");

			}
			List<Inter> v2 =  new ArrayList<Inter>();
			String a = v.get(i).getInterest();
			
            if (a != null) {
               String[] atmp = a.split(",");
               for (int j = 0; j < atmp.length; j++) {
                  Inter it = new Inter(atmp[j]);
                  v2.add(it);
               }
               v.get(i).setInterests(v2);
            }
		}

    		return new ResponseEntity<List<RecruitPjtPinterest>>(v, HttpStatus.OK);
		}

		/*
		List<RecruitPjtPinterest> original = rService.selectAllRecruitPjtPinterest();
		List<RecruitPjtPinterest> ret = new ArrayList<RecruitPjtPinterest>();
		int len = original.size();
		// 원본 리스트에서 rnum이 겹치는 부분은 pinterest를 "",""로 합치기
		int index = 0;
		outer : while(true) {
			int firstRnum = original.get(index).getRnum();
			RecruitPjtPinterest tmp = original.get(index);
			while(true) {
				String tmpInterest = tmp.getInterest();
				index++;
				if(index == len) {
					ret.add(tmp);
					break outer;
				}
				if(firstRnum != original.get(index).getRnum()) {
					ret.add(tmp);
					break;
				}else {
					// 다음번 요소가 겹침
					tmpInterest += ("," + original.get(index).getInterest());
					tmp.setInterest(tmpInterest);
				}
			}
			//index++;			
		}
		System.out.println("testing");
		for(RecruitPjtPinterest r : ret) {
			System.out.println(r.toString());
		}
		return new ResponseEntity<List<RecruitPjtPinterest>>(ret, HttpStatus.OK);
		*/
	

	@ApiOperation(value = "모든 구인구직 게시판의 정보를 pjtName과 pinterest도 함께 반환한다.", response = List.class)
	@GetMapping("/selectAllRecruitPjtPinterest/{paging}&cnt={cnt}")
	public ResponseEntity<List<RecruitPjtPinterest>> selectAllRecruitPjtPinterestLimit(@PathVariable int paging, @PathVariable int cnt) throws Exception {
		Two<Integer, Integer> two = new Two<Integer,Integer>(paging* cnt, cnt);
		List<RecruitPjtPinterest> original = rService.selectAllRecruitPjtPinterestLimit(two);
		List<RecruitPjtPinterest> ret = new ArrayList<RecruitPjtPinterest>();
		int len = original.size();
		// 원본 리스트에서 rnum이 겹치는 부분은 pinterest를 "",""로 합치기
		int index = 0;
		outer : while(true) {
			int firstRnum = original.get(index).getRnum();
			RecruitPjtPinterest tmp = original.get(index);
			while(true) {
				String tmpInterest = tmp.getInterest();
				index++;
				if(index == len) {
					ret.add(tmp);
					break outer;
				}
				if(firstRnum != original.get(index).getRnum()) {
					ret.add(tmp);
					break;
				}else {
					// 다음번 요소가 겹침
					tmpInterest += ("," + original.get(index).getInterest());
					tmp.setInterest(tmpInterest);
				}
			}
			//index++;			
		}
		System.out.println("testing");
		for(RecruitPjtPinterest r : ret) {
			System.out.println(r.toString());
		}
		return new ResponseEntity<List<RecruitPjtPinterest>>(ret, HttpStatus.OK);
	}
/*
	@ApiOperation(value = "모든 구인구직 게시판의 정보를 소팅해서 반환한다.", response = List.class)
	@GetMapping("/sorting/{id}")
	public ResponseEntity<List<RecruitPjt>> retrieveBoard(@PathVariable String id) throws Exception {
		List<RecruitPjt> list = rService.selectAll();
		List<Interest> iList = iService.selectById(id);
		list.sort(new Comparator<RecruitPjt>() {

			@Override
			public int compare(RecruitPjt o1, RecruitPjt o2) {
				List<Pinterest> pi1 = piService.select(o1.getPid());
				List<Pinterest> pi2 = piService.select(o2.getPid());
				Integer cnt1 = 0, cnt2 = 0;
				for (int i = 0; i < iList.size(); i++) {
					String ui = iList.get(i).getInterest();
					for (int j = 0; j < pi1.size(); j++) {
						if (pi1.get(j).getInterest().equals(ui)) {
							cnt1++;
						}

						if (pi2.get(j).getInterest().equals(ui)) {
							cnt2++;
						}
					}
				}

				return cnt2.compareTo(cnt1);
			}
		});

		return new ResponseEntity<List<RecruitPjt>>(rService.selectAll(), HttpStatus.OK);
	}
*/
	@ApiOperation(value = "해당 지역에 거주하는 게시판의 정보를 반환한다..", response = List.class)
	@GetMapping("/addr/sido={sido}&gugun={gugun}&dong={dong}")
	public ResponseEntity<List<Recruit>> selectByAddr(@PathVariable String sido, @PathVariable String gugun,
			@PathVariable String dong) throws Exception {
		Addr v = new Addr();
		v.setDong(dong);
		v.setGugun(gugun);
		v.setSido(sido);
		//v.setPcnt(cnt);
		//v.setPaging(paging* cnt);
		return new ResponseEntity<List<Recruit>>(rService.selectByAddr(v), HttpStatus.OK);
	}

	@ApiOperation(value = "완전히 일치하는 유저의 id를 반환한다.", response = Usertag.class)
	@GetMapping("selectSame/{tag}")
	public ResponseEntity<List<Recruit>> selectSame(@PathVariable String tag) {
		TagList v = new TagList();
		//v.setPcnt(cnt);
		//v.setPaging(paging);
		String a[] = tag.split(",");
		int b = a.length;
		if (a.length > 0)
			v.setTag1(a[0]);
		if (a.length > 1)
			v.setTag2(a[1]);
		if (a.length > 2)
			v.setTag3(a[2]);
		if (a.length > 3)
			v.setTag4(a[3]);
		if (a.length > 4)
			v.setTag5(a[4]);
		v.setCnt(b);
		// 어차피 널이 들어감.

		return new ResponseEntity<List<Recruit>>(rService.selectSame(v), HttpStatus.OK);
	}

	@ApiOperation(value = "태그와 주소 혼합해서 검색하는 것.", response = Usertag.class)
	@GetMapping("selectAddrAndTag/tag={tag}&addr={addr}")
	public ResponseEntity<List<Recruit>> selectAddrAndTag(@PathVariable String tag, @PathVariable String addr) {
		AddrAndTag v = new AddrAndTag();
		//v.setPcnt(cnt);
		//v.setPaging(paging* cnt);
		String b[] = addr.split(",");
		if (tag == null) {
			v.setDong(b[0]);
			v.setGugun(b[1]);
			v.setSido(b[2]);
			v.setCnt(0);

			for (String string : b) {
				System.out.println(string);
			}
			return new ResponseEntity<List<Recruit>>(rService.selectAddrAndTag(v), HttpStatus.OK);

		} else {
			String a[] = tag.split(",");

			if (a.length > 0)
				v.setTag1(a[0]);
			if (a.length > 1)
				v.setTag2(a[1]);
			if (a.length > 2)
				v.setTag3(a[2]);
			if (a.length > 3)
				v.setTag4(a[3]);
			if (a.length > 4)
				v.setTag5(a[4]);
			v.setCnt(a.length);
			v.setSido(b[0]);
			v.setGugun(b[1]);
			v.setDong(b[2]);
			// 어차피 널이 들어감.
			for (String string : a) {
				System.out.println(string);
			}
			for (String string : b) {
				System.out.println(string);
			}
			return new ResponseEntity<List<Recruit>>(rService.selectAddrAndTag(v), HttpStatus.OK);

		}
	}

	@ApiOperation(value = "모든 검색어 통합 검색하는 것.", response = Recruit.class)
	@GetMapping("search/tag={tag}&addr={addr}&by={by}&keyword={keyword}")
	public ResponseEntity<List<RecruitPjtPinterest>> search(@PathVariable String tag, @PathVariable String addr,
			@PathVariable String by, @PathVariable String keyword) {
		SearchParameter sp = new SearchParameter();
	      //sp.setPaging(paging* cnt);
	      //sp.setPcnt(cnt);
		
		
		List<RecruitPjtPinterest> ret = new ArrayList<RecruitPjtPinterest>();
		
		
		String b[] = addr.split(",");
		if (tag.equals("null")) {
			
			// tag 기술 스택이 없는 경우
			//System.out.println("asdasdasd");
			sp.setBy(by);
			sp.setKeyword(keyword);
			sp.setDong(b[0]);
			sp.setGugun(b[1]);
			sp.setSido(b[2]);
			sp.setCnt(0);
			
			//System.out.println("sp 확인");
			//System.out.println(sp.toString());
			List<RecruitPjtPinterest> v = rService.searchAll(sp);

			for (int i = 0; i < v.size(); i++) {
				int result = new Date().compareTo(v.get(i).getEndDate());

				if (result >= 0) { // 앞에 있는게 뒤에있는거보다 더 느리다는 뜻

					v.get(i).setRstate("기한만료");
				} else if (result == -1) { // 앞에 있는게 뒤에있는거보다 더 빠르다는 뜻
					if (v.get(i).getPjtMemberCnt() <= v.get(i).getCnt()) {
						v.get(i).setRstate("모집완료");
					} else
						v.get(i).setRstate("모집중");

				}
				List<Inter> v2 =  new ArrayList<Inter>();
				String a = v.get(i).getInterest();
				
	            if (a != null) {
	               String[] atmp = a.split(",");
	               for (int j = 0; j < atmp.length; j++) {
	                  Inter it = new Inter(atmp[j]);
	                  v2.add(it);
	               }
	               v.get(i).setInterests(v2);
	            }
			}

	    		return new ResponseEntity<List<RecruitPjtPinterest>>(v, HttpStatus.OK);
			/*
			List<RecruitPjtPinterest> original = rService.searchAll(sp);
			
			int len = original.size();
			//System.out.println("size is "+ len);
			
			for(int i = 0 ; i < len; i++) {
				//System.out.println(original.get(i).toString());
			}
			
			// 원본 리스트에서 rnum이 겹치는 부분은 pinterest를 "",""로 합치기
			int index = 0;
			if(original.size() == 0) {
				return new ResponseEntity<List<RecruitPjtPinterest>>(ret, HttpStatus.OK);
			}
			outer : while(true) {
				int firstRnum = original.get(index).getRnum();
				RecruitPjtPinterest tmp = original.get(index);
				while(true) {
					String tmpInterest = tmp.getInterest();
					index++;
					if(index == len) {
						ret.add(tmp);
						break outer;
					}
					if(firstRnum != original.get(index).getRnum()) {
						ret.add(tmp);
						break;
					}else {
						// 다음번 요소가 겹침
						tmpInterest += ("," + original.get(index).getInterest());
						tmp.setInterest(tmpInterest);
					}
				}
				//index++;			
			}
			return new ResponseEntity<List<RecruitPjtPinterest>>(ret, HttpStatus.OK);
*/
		} else {
			// 기술 스택 태그가 있는 경우
			String a[] = tag.split(",");
			//System.out.println("tag is " + tag);
			//System.out.println("a 길이는 " + a.length);

			if (a.length > 0)
				sp.setTag1(a[0]);
			if (a.length > 1)
				sp.setTag2(a[1]);
			if (a.length > 2)
				sp.setTag3(a[2]);
			if (a.length > 3)
				sp.setTag4(a[3]);
			if (a.length > 4)
				sp.setTag5(a[4]);
			sp.setCnt(a.length);
			sp.setSido(b[0]);
			sp.setGugun(b[1]);
			sp.setDong(b[2]);
			sp.setBy(by);
			sp.setKeyword(keyword);
			// 어차피 널이 들어감.
			

			List<RecruitPjtPinterest> v = rService.searchAll(sp);

			for (int i = 0; i < v.size(); i++) {
				int result = new Date().compareTo(v.get(i).getEndDate());

				if (result >= 0) { // 앞에 있는게 뒤에있는거보다 더 느리다는 뜻

					v.get(i).setRstate("기한만료");
				} else if (result == -1) { // 앞에 있는게 뒤에있는거보다 더 빠르다는 뜻
					if (v.get(i).getPjtMemberCnt() <= v.get(i).getCnt()) {
						v.get(i).setRstate("모집완료");
					} else
						v.get(i).setRstate("모집중");

				}
				List<Inter> v2 =  new ArrayList<Inter>();
				String aa = v.get(i).getInterest();
				
	            if (aa != null) {
	               String[] atmp = aa.split(",");
	               for (int j = 0; j < atmp.length; j++) {
	                  Inter it = new Inter(atmp[j]);
	                  v2.add(it);
	               }
	               v.get(i).setInterests(v2);
	            }
			}

	    		return new ResponseEntity<List<RecruitPjtPinterest>>(v, HttpStatus.OK);
			/*
			List<RecruitPjtPinterest> original = rService.searchAll(sp);
			
			int len = original.size();
			if(original.size() == 0) {
				return new ResponseEntity<List<RecruitPjtPinterest>>(ret, HttpStatus.OK);
			}
			// 원본 리스트에서 rnum이 겹치는 부분은 pinterest를 "",""로 합치기
			int index = 0;
			outer : while(true) {
				int firstRnum = original.get(index).getRnum();
				RecruitPjtPinterest tmp = original.get(index);
				while(true) {
					String tmpInterest = tmp.getInterest();
					index++;
					if(index == len) {
						ret.add(tmp);
						break outer;
					}
					if(firstRnum != original.get(index).getRnum()) {
						ret.add(tmp);
						break;
					}else {
						// 다음번 요소가 겹침
						tmpInterest += ("," + original.get(index).getInterest());
						tmp.setInterest(tmpInterest);
					}
				}
				//index++;			
			}
			return new ResponseEntity<List<RecruitPjtPinterest>>(ret, HttpStatus.OK);
*/
		}
	}

	@ApiOperation(value = "글번호에 해당하는 구인구직 게시판의 정보를 반환한다.", response = Recruit.class)
	@GetMapping("{rnum}")
	public ResponseEntity<Recruit> detailBoard(@PathVariable int rnum) {
		return new ResponseEntity<Recruit>(rService.select(rnum), HttpStatus.OK);
	}

	@ApiOperation(value = "새로운 구인구직 게시판 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping
	public ResponseEntity<String> writeBoard(@RequestBody Recruit r) {
		if (rService.insert(r) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "글번호에 해당하는 프로젝트의 정보를 수정한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("{rnum}")
	public ResponseEntity<String> updateBoard(@RequestBody Recruit r) {

		if (rService.update(r) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "글번호에 해당하는 프로젝트의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("{rnum}")
	public ResponseEntity<String> deleteBoard(@PathVariable int rnum) {
		Recruit tmp = rService.select(rnum);
		List<Pinterest> list = piService.select(tmp.getPid());
		for (Pinterest pi : list) {
			piService.delete(pi);
		}
		if (rService.delete(rnum) != 0) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}
