package com.test.junit.program;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfilePool {
    private List<Profile> profiles = new ArrayList<Profile>();

    public void add(Profile profile) {
        profiles.add(profile);
    }

    public void score(Criteria criteria) {
        for (Profile profile: profiles)
            profile.getMatchSet(criteria).matches();
    }

//    public List<Profile> ranked() {
//        Collections.sort(profiles,
//                (p1, p2) -> ((Integer)p2.score()).compareTo(p1.score()));
//        return profiles;
//    }
}
