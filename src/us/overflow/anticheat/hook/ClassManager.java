package us.overflow.anticheat.hook;

import lombok.Getter;
import us.overflow.anticheat.OverflowAPI;
import us.overflow.anticheat.data.type.CheckManager;
import us.overflow.anticheat.utils.MathUtil;
import us.overflow.anticheat.utils.http.HTTPUtil;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created on 28/04/2020 Package us.overflow.anticheat.hook
 */
@Getter
public final class ClassManager {

    private static ClassManager classManager;

    private List<String> resolvedStrings = new CopyOnWriteArrayList<>();

    public boolean flag = false;

    private String s1, s2;

    public ClassManager() {
        classManager = this;
    }

    public void start() {
          try {
        	
            Class.forName("us.overflow.anticheat.hook.HookManager").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
