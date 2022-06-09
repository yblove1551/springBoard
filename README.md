# springBoard - 계층형 댓글 게시판(개인프로젝트)

신입 웹개발직 지원을 위한 포트폴리오 입니다.

## 기능소개 

회원 가입 후 게시글과 게시글에 대한 댓글을 남길 수 있는 답변형 게시판입니다


회원가입시엔 이메일을 본인확인용 key로 이용하고 있으며,

정규식 패턴을 사용해 이메일의 유효성을 검증하게 하였으며 

본인 인증을 위해 javax.mail 라이브러리를 이용하였습니다.

게시글 작성과 댓글 작성은 비교적 비슷하지만, 

게시글 안에서 작성하는 댓글기능은 화면의 이동 없이 

즉시 댓글을 작성할 수 있게 ajax를 이용해 구현했으며

ajax로 조회한 댓글은 오라클의 with 구문을 이용해 계층형 댓글로 

조회할 수 있게 하여 댓글간의 관계를 더 쉽게 파악할 수 있게 하였습니다.


또한 회원 탈퇴시 작성한 글은 모두 삭제되게,

작성한 글이 삭제될땐 글에 달린 모든 댓글이,

댓글이 삭제될 때는 하위 댓글까지 모두 삭제할 수 있게 

@Transactinal 에너테이션을 이용해 여러 작업을 트랜젹션으로 묶어

데이터베이스의 무결성을 유지할 수 있게 노력했습니다.




## 개발환경
### front
+ JQuery
+ BootStrab
### back
+ spring 5.2
+ JDK 11.0.13
+ Oracle 11g Enterprise Edition
+ Mybatis 3.4
+ Tomcat 9
+ Maven 3.8.4
---------

## 제작기간
  1개월(2주 완성 및 틈틈히 수정)



## 테이블구조


![erd 다시](https://user-images.githubusercontent.com/86616901/172877769-13d4676d-e906-4e7a-9307-1dc1d67ae397.JPG)

## 회원 등록
https://blog.naver.com/yblove1551/222766628121

## 회원 수정 및 삭제
https://blog.naver.com/yblove1551/222766630484

## 게시글 CRUD
https://blog.naver.com/yblove1551/222766632751

## 계층형 댓글 CRUD
https://blog.naver.com/yblove1551/222766635913






