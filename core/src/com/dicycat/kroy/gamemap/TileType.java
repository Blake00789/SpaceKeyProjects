package com.dicycat.kroy.gamemap;


import java.util.HashMap;

public enum TileType {
	
//	PAVEMENT1(8, true),
//	PAVEMENT2(9, true),
//	PAVEMENT3(10, true),
//	PAVEMENT4(11, true),
//	PAVEMENT5(12, true),
//	PAVEMENT6(13, true),
//	PAVEMENT7(14, true),
//	PAVEMENT8(15, true),
//	PAVEMENT9(35, true),
//	PAVEMENT10(36, true),
//	PAVEMENT11(37, true),
//	PAVEMENT12(38, true),
//	PAVEMENT13(39, true),
//	PAVEMENT14(40, true),
//	PAVEMENT15(41, true),
//	PAVEMENT16(42, true),
//	PAVEMENT17(62, true),
//	PAVEMENT18(63, true),
//	PAVEMENT19(64, true),
//	PAVEMENT20(65, true),
//	PAVEMENT21(66, true),
//	PAVEMENT22(67, true),
//	PAVEMENT23(68, true),
//	PAVEMENT24(69, true),
//	ROAD1(406, false),
//	ROAD2(407, false),
//	ROAD3(408, false),
//	ROAD4(433, false),
//	ROAD5(434, false),
//	ROAD6(435, false),
//	ROAD7(436, false),
//	ROAD8(437, false),
//	ROAD9(438, false),
//	ROAD10(439, false),
//	ROAD11(440, false),
//	ROAD12(441, false),
//	ROAD13(442, false),
//	ROAD14(443, false),
//	ROAD15(460, false),
//	ROAD16(461, false),
//	ROAD17(462, false),
//	ROAD18(463, false),
//	ROAD19(464, false),
//	ROAD20(465, false),
//	ROAD21(466, false),
//	ROAD22(467, false),
//	ROAD23(468, false),
//	ROAD24(469, false),
//	ROAD25(470, false),
//	REDBRICK(76, false),
	NONCOLLIDABLE(422,false),
	COLLIDABLE(421,true)
	;

	
	private int id;
	private boolean collidable;
	
	public static final int TILE_SIZE = 16;
	
	
	private TileType(int id, boolean collidable) {
		this.id = id;
		this.collidable = collidable;
	}

	public int getId() {
		return id;
	}

	public boolean isCollidable() {
		return collidable;
	}


	
	private static HashMap<Integer, TileType> tileMap;
	
	static {
		tileMap = new HashMap<Integer, TileType>();
		for (TileType tileType: TileType.values()) {
			tileMap.put(tileType.getId(), tileType);
		}
	}
	
	public static TileType getTileTypeByID(int id) {
		return tileMap.get(id);
	}

}
