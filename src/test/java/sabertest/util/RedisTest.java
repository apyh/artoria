package sabertest.util;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import saber.util.Redis;

public class RedisTest {
    private Redis redis;

    @Before
    public void init() {
        redis = Redis.on("share0.oaw.me")
                .setPassword("+5WZSDV3CZU2PPW+").init();
    }

    @Test
    public void test1() {
        // 封装的 方法 额外 的 消耗
        // 每次 调用 都会 从 池里面取出，用完 再 放回去
        System.out.println(redis.ping());
        System.out.println(redis.set("123", "456"));
        System.out.println(redis.get("123"));
    }

    @Test
    public void test2() {
        // 正数 和 负数 出现 概念 比较 均匀，所以 性能 无影响
        for (int i = 0; i < 300; i++) {
            doSet();
        }
    }

    public void doSet() {
        redis.flushDB();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
//            System.out.print(redis.set("V" + i, "V" + i) + " ");
            redis.set("V" + i, "V" + i);
        }
        long end = System.currentTimeMillis();
        long handle1 = end - start;

//        System.out.println();

        start = System.currentTimeMillis();
        Jedis jedis = redis.getJedis();
        for (int i = 0; i < 100; i++) {
//            System.out.print(jedis.set("Q" + i, "Q" + i) + " ");
            jedis.set("Q" + i, "Q" + i);
        }
        jedis.close();
        end = System.currentTimeMillis();
        long handle2 = end - start;

//        System.out.println();
//        System.out.println(handle1);
//        System.out.println(handle2);
        System.out.print(handle1 - handle2);
        System.out.print(" ");
    }

}