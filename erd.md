```mermaid
erDiagram
    %% 1. 계정 및 권한 관련
    user_tb ||--o| channel_tb : "1:1 소유 (이메일 인증 필수)"
    user_tb ||--o{ follow_tb : "팔로워 (구독자)"
    user_tb ||--o{ alarm_tb : "알림 수신"
    user_tb ||--o{ community_tb : "게시물 작성"
    user_tb ||--o{ comment_tb : "댓글 작성"
    user_tb ||--o{ re_comment_tb : "대댓글 작성"
    user_tb ||--o{ chat_tb : "채팅 발신"

    %% 2. 채널 중심 관계
    channel_tb ||--o{ follow_tb : "팔로워 보유"
    channel_tb ||--o{ live_streaming_tb : "라이브 송출"
    channel_tb ||--o{ clip_tb : "클립/쇼츠 보유"
    channel_tb ||--o{ community_tb : "채널 게시판 관리"

    %% 3. 라이브 및 미디어 관계
    live_streaming_tb ||--o| review_tb : "방송 종료 시 VOD 생성"
    live_streaming_tb ||--o| chat_room_tb : "방송용 채팅방 할당"
    chat_room_tb ||--o{ chat_tb : "메시지 기록"

    %% 4. 커뮤니티 상세 관계
    community_tb ||--o{ comment_tb : "댓글 포함"
    comment_tb ||--o{ re_comment_tb : "부모-자식 관계"

    user_tb {
        bigint user_id PK
        string email UK
        string password
        string nickname
        enum role "USER, STREAMER, ADMIN"
        boolean is_verified
        string oauth_provider
        datetime created_at
    }

    channel_tb {
        bigint channel_id PK
        bigint user_id FK "Unique"
        string channel_name
        text description
        string stream_key UK
        int follower_count
        datetime created_at
    }

    follow_tb {
        bigint follow_id PK
        bigint user_id FK "Follower"
        bigint channel_id FK "Target Channel"
        datetime created_at
    }

    alarm_tb {
        bigint alarm_id PK
        bigint user_id FK "Receiver"
        enum type "LIVE, POST, COMMENT"
        string message
        boolean is_read
        datetime created_at
    }

    live_streaming_tb {
        bigint stream_id PK
        bigint channel_id FK
        string title
        string category
        int viewer_count
        enum status "ON, OFF"
        datetime started_at
    }

    review_tb {
        bigint review_id PK
        bigint stream_id FK "Unique"
        string video_url
        time duration
        int view_count
        datetime created_at
    }

    clip_tb {
        bigint clip_id PK
        bigint channel_id FK
        string title
        string video_url
        int view_count
        datetime created_at
    }

    community_tb {
        bigint post_id PK
        bigint channel_id FK
        bigint user_id FK "Author"
        string title
        text content
        int share_count
        datetime created_at
        datetime updated_at
    }

    comment_tb {
        bigint comment_id PK
        bigint post_id FK
        bigint user_id FK
        text content
        datetime created_at
    }

    re_comment_tb {
        bigint re_id PK
        bigint comment_id FK "Parent Comment"
        bigint user_id FK
        text content
        datetime created_at
    }

    chat_room_tb {
        bigint room_id PK
        bigint stream_id FK "Unique"
        datetime created_at
    }

    chat_tb {
        bigint chat_id PK
        bigint room_id FK
        bigint user_id FK
        text message
        datetime sent_at
    }
