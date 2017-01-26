package netdisk;

import java.util.Arrays;

public class Test {
	
	public static void main(String[] args) {
		String[] ids = {"7","8","24","36","43","44","51","58","59","60","64","67","73","74","75","78","79","80","81","87","94","95","96","97","98"};
		Arrays.stream(ids).forEach(id->{
			System.out.println("INSERT INTO distributor_advertisements (distributor_id, img_path, rate, link, create_at) VALUES ("+id+", \"http://imgcdn.renminyixue.com/507d9458-23bb-4be1-bcfb-2b2403595b44\",\"0\",\"http://shop.renminyixue.com/xczt/index.html\", \"1484813743891\");");
		});
		
	}
	
}
