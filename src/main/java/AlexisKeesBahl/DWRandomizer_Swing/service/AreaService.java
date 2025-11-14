package AlexisKeesBahl.DWRandomizer_Swing.service;

import AlexisKeesBahl.DWRandomizer_Swing.data.DungeonArrays;
import AlexisKeesBahl.DWRandomizer_Swing.model.Area;
import AlexisKeesBahl.DWRandomizer_Swing.model.AreaDanger;
import AlexisKeesBahl.DWRandomizer_Swing.model.AreaDiscovery;
import AlexisKeesBahl.DWRandomizer_Swing.repository.AreaRepository;
import AlexisKeesBahl.DWRandomizer_Swing.service.crud.IGenericCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static AlexisKeesBahl.DWRandomizer_Swing.model.util.Rolls.*;

@Service
public class AreaService implements IGenericService<Area>, IGenericCRUDService<Area> {

    @Autowired
    private AreaDangerService areaDangerService;
    @Autowired
    private AreaDiscoveryService areaDiscoveryService;
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<Area> list() {
        return areaRepository.findAll();
    }

    @Override
    public Area searchById(Integer id) {

        return areaRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Area area) {
        areaRepository.save(area);
    }

    @Override
    public void delete(Area area) {
        areaRepository.delete(area);
    }



    public void rollArea(Area area) {
        area.setAreaType(PickFrom(DungeonArrays.AREA_TYPE));
        area.setOneLiner(area.getAreaType());
        rollAreaDetails(area);
        area.setRarity(PickFrom(DungeonArrays.AREA_RARITY));



        area.setAreaDressing(PickFrom(DungeonArrays.AREA_DRESSING));
        if (Objects.equals(area.getAreaDressing(),"roll twice") || Objects.equals(area.getAreaDressing(),"ROLL TWICE")) area.setAreaDressing(rollTwice(DungeonArrays.AREA_DRESSING));

    }

    public void rollAreaDetails(Area area){
        int rollRarity = UniversalRoll(DungeonArrays.AREA_RARITY);
        switch (rollRarity){
            case 0 ->{
                area.setDangersAmount(Roll1d4()+1);
                area.setDiscoveriesAmount(0);
            }
            case 1,2 ->{
                area.setDangersAmount(1);
                area.setDiscoveriesAmount(0);
            }
            case 3,4,5->{
                area.setDangersAmount(0);
                area.setDiscoveriesAmount(0);
            }
            case 6->{
                area.setDangersAmount(1);
                area.setDiscoveriesAmount(1);
            }
            case 7->{
                area.setDangersAmount(Roll1d4()+1);
                area.setDiscoveriesAmount(1);
            }
            case 8->{
                area.setDangersAmount(0);
                area.setDiscoveriesAmount(1);
            }
            case 9->{
                area.setDangersAmount(Roll1d4()+1);
                area.setDiscoveriesAmount(Roll1d4()+1);
            }
            case 10->{
                area.setDangersAmount(1);
                area.setDiscoveriesAmount(Roll1d4()+1);
            }
            case 11->{
                area.setDangersAmount(0);
                area.setDiscoveriesAmount(Roll1d4()+1);
            }

        }

        area.setRarity(DungeonArrays.AREA_RARITY[rollRarity]);

        addDangers(area);
        addDiscoveries(area);
    }

    public void addDiscoveries(Area area){
        List<AreaDiscovery> list = new ArrayList<>();

        if (area.getDiscoveriesAmount()>0) {
            int i;
            for (i = 1; i <= area.getDiscoveriesAmount(); i++) {
                AreaDiscovery d = new AreaDiscovery();
                areaDiscoveryService.rollAreaDiscovery(d);
                list.add(d.clone());
            }
            area.setDiscoveries(list);
        }

    }


    public void addDangers(Area area){
        List<AreaDanger> list = new ArrayList<>();

        if (area.getDangersAmount()>0) {
            int i;
            for (i = 1; i <= area.getDangersAmount(); i++) {
                AreaDanger d = new AreaDanger();
                areaDangerService.rollAreaDanger(d);
                list.add(d.clone());
            }
        }
        area.setDangers(list);
    }


}
