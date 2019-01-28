package com.jambit.lunchlottery.lunchlottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections4.ListUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by mkirsch on 25.04.18.
 */

@RestController
public class LunchLotteryController {


  @RequestMapping("/")
  public String heartBeat() {
    return "alive";
  }

  @RequestMapping("/lottery")
  public String lottery(
      @RequestParam(value = "persons", defaultValue = "4") final Integer groupSize,
      @RequestBody final String body) {

    final Pattern pattern = Pattern.compile("([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)");
    final List<String> list = new ArrayList<String>();
    final Matcher m = pattern.matcher(body);
    while (m.find()) {
      list.add(m.group());
    }

    for (int i = 0; i < 100; i++) {
      Collections.shuffle(list);
    }

    final List<List<String>> groups = ListUtils.partition(list, groupSize);

    final ArrayList<String> guestlist = new ArrayList<>();

    final String prefix =
        "Der Glückswürfel ist gefallen und beschenkt euch mit einer noch nie da gewesenen "
            + "Gruppenaufteilung. ;)\n"
            +
            "\n" +
            "Wie immer sind alle Angaben ohne Gewehr und Gewähr! \n" +
            "\n" +
            "Der oder die erste in der Liste mit einem Sternchen gekennzeichnet, ist für die "
            + "Gruppenorganisation verantwortlich. "
            +
            "Die Performance bei der Planung wird vom Zentralrat der Fliesentischbesitzer, der NSA "
            + "und Thomas kontrolliert und hat direkten Einfluss auf euer Gehalt - oder auch nicht."
            + " ;p "
            +
            "Also nicht lange trödeln, sondern gleich loslegen!" +
            "Bitte organisiert euch selbst und habt ein tolles Mittagessen.\n" +
            "Liebe Grüße,\n" +
            "Cherry\n" +
            " \n" +
            " \n";

    for (final List<String> group : groups) {
      guestlist.add("Gruppe: " + String.valueOf(groups.indexOf(group) + 1));
      for (final String person : group) {
        if (group.indexOf(person) == 0) {
          guestlist.add("*" + person);
        } else {
          guestlist.add(person);
        }
      }
      guestlist.add("\n");
    }

    final StringBuilder sb = new StringBuilder();

    sb.append(prefix);

    for (final String guest : guestlist) {
      System.out.println(guest);
      sb.append(guest + "\n");
    }

    return sb.toString();
  }

}
