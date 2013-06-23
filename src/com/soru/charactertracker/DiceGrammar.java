package com.soru.charactertracker;

import org.petitparser.parser.CompositeParser;
import static org.petitparser.Chars.anyOf;
import static org.petitparser.Chars.character;
import static org.petitparser.Chars.digit;
import java.util.List;

import com.google.common.base.Function;

public class DiceGrammar extends CompositeParser {
	protected void initialize() {
		def("number", 
		    character('-').star()
		    .seq(digit().plus())
		    .flatten().trim().map(new Function<String, Integer>() {
				@Override
				public Integer apply(String arg0) {
					return Integer.parseInt(arg0);
				}
		    }));
		
		def("roll", 
		    ref("number").optional()
		    .seq(character('d').or(character('D')))
		    .seq(ref("number"))
		    .map(new Function<List<?>, DieRoll>() {
				@Override
				public DieRoll apply(List<?> list) {
					int num = list.get(0) == null ? 1 : Integer.parseInt(list.get(0).toString());
					int size = Integer.parseInt(list.get(2).toString());
					
					return new DieRoll(num, size, 0); //Lists.newArrayList(1, list.get(2), Lists.newArrayList());
				}
		    }
		   ));
		
		def("element",
			ref("roll")
		    .or(ref("number"))
		    );
		
		def("result",
		    ref("element")
		    .separatedBy(anyOf("+-").trim()));  // includeSeparator: true
		
		def("calculation",
		    ref("result"));
		
		def("start", ref("calculation").end());
	}
}