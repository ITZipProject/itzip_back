db.categories.insertMany([
    // 소프트웨어 개발 카테고리
    {
        _id: ObjectId(),
        category_type: "SOFTWARE_DEVELOPMENT",
        category_detail: "PROGRAMMING_LANGUAGE"
    },
    {
        _id: ObjectId(),
        category_type: "SOFTWARE_DEVELOPMENT",
        category_detail: "WEB_DEVELOPMENT"
    },
    {
        _id: ObjectId(),
        category_type: "SOFTWARE_DEVELOPMENT",
        category_detail: "MOBILE_DEVELOPMENT"
    },
    {
        _id: ObjectId(),
        category_type: "SOFTWARE_DEVELOPMENT",
        category_detail: "GAME_DEVELOPMENT"
    },

    // 시스템 & 인프라 카테고리
    {
        _id: ObjectId(),
        category_type: "SYSTEM_INFRA",
        category_detail: "DEVOPS"
    },
    {
        _id: ObjectId(),
        category_type: "SYSTEM_INFRA",
        category_detail: "DATABASE"
    },
    {
        _id: ObjectId(),
        category_type: "SYSTEM_INFRA",
        category_detail: "CLOUD"
    },
    {
        _id: ObjectId(),
        category_type: "SYSTEM_INFRA",
        category_detail: "SECURITY_NETWORK"
    },

    // 테크 카테고리
    {
        _id: ObjectId(),
        category_type: "TECH",
        category_detail: "AI"
    },
    {
        _id: ObjectId(),
        category_type: "TECH",
        category_detail: "DATA_SCIENCE"
    },
    {
        _id: ObjectId(),
        category_type: "TECH",
        category_detail: "BLOCKCHAIN"
    },
    {
        _id: ObjectId(),
        category_type: "TECH",
        category_detail: "VR_AR"
    },
    {
        _id: ObjectId(),
        category_type: "TECH",
        category_detail: "HARDWARE"
    },

    // 디자인 & 아트 카테고리
    {
        _id: ObjectId(),
        category_type: "DESIGN_ART",
        category_detail: "UI_UX"
    },
    {
        _id: ObjectId(),
        category_type: "DESIGN_ART",
        category_detail: "GRAPHICS"
    },
    {
        _id: ObjectId(),
        category_type: "DESIGN_ART",
        category_detail: "MODELING_3D"
    },
    {
        _id: ObjectId(),
        category_type: "DESIGN_ART",
        category_detail: "SOUND"
    },

    // 비즈니스 카테고리
    {
        _id: ObjectId(),
        category_type: "BUSINESS",
        category_detail: "OFFICE"
    },
    {
        _id: ObjectId(),
        category_type: "BUSINESS",
        category_detail: "PLANNING_PM"
    },
    {
        _id: ObjectId(),
        category_type: "BUSINESS",
        category_detail: "AUTOMATION"
    },
    {
        _id: ObjectId(),
        category_type: "BUSINESS",
        category_detail: "MARKETING"
    },

    // 기타 카테고리
    {
        _id: ObjectId(),
        category_type: "OTHER",
        category_detail: "OTHER"
    }
]);