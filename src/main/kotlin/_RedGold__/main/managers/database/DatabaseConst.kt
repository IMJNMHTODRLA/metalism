package _RedGold__.main.managers.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

fun databaseDefaultConfig(absolutePath: String, fileName: String): HikariDataSource {
    return HikariDataSource(HikariConfig().apply {
        jdbcUrl = "jdbc:sqlite:$absolutePath/$fileName"
        driverClassName = "org.sqlite.JDBC"

        // 풀 관리 최적화 (SQLite는 단일 쓰기에 최적화되어 풀이 작을수록 안정적)
        maximumPoolSize = 1
        minimumIdle = 1
        idleTimeout = 30000
        connectionTimeout = 10000
        leakDetectionThreshold = 2000 // 커넥션 누수 감지 (2초)

        // 커넥션 테스트 쿼리 (연결 유효성 검사)
        connectionTestQuery = "SELECT 1"

        connectionInitSql = """
            PRAGMA journal_mode = WAL;
            PRAGMA synchronous = NORMAL;
            PRAGMA busy_timeout = 5000;
            PRAGMA foreign_keys = true;
            
            PRAGMA cache_size = -16000;
            PRAGMA temp_store = MEMORY;
            PRAGMA mmap_size = 2147483648;
            PRAGMA auto_vacuum = INCREMENTAL;
            PRAGMA encoding = 'UTF-8';
        """.trimIndent()
    })
}